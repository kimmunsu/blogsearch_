package com.kms.blogsearch

import com.kms.blogsearch.domain.BlogSearchKeyword
import com.kms.blogsearch.domain.PopularKeyword
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDateTime

/**
 * batch job config
 *
 * reader
 *  1. 100 단위의 chunk 로
 *  2. 이미 카운팅된 배치 수행되었던 데이터를 제외한 모든 키워드 데이터를 조회 - reader
 * processor
 *  1. in memory 를 통해 키워드 카운팅
 *  2. 카운팅이 끝난 키워드 데이터는 processedDtm = batch dtm 을 통해서 수행한 것으로 update
 * writer
 *  1. 카운팅된 데이터 저장
 */
@Configuration
class PopularKeywordJobConfiguration(
    val blogSearchKeywordRepository: BlogSearchKeywordPersistenceAdapter,
    val popularKeywordRepository: PopularKeywordPersistenceAdapter,
    val entityManagerFactory: EntityManagerFactory
) : JobExecutionListener {

    // list reader, list processor, list writer 에 대한 구현중 진행이 안되어 인메모리 사용.
    lateinit var keywordCountMap: MutableMap<String, PopularKeyword>

    companion object {
        const val JOB_NAME = "popularKeywordJob"
        const val STEP_NAME = "popularKeywordStep"
        const val READER_NAME = "keywordReader"
        const val CHUNK_SIZE = 100
    }

    @Bean
    fun popularKeywordJob(jobRepository: JobRepository, platformTransactionManager: PlatformTransactionManager): Job {
        val batchExecuteDtm = LocalDateTime.now()
        return JobBuilder(JOB_NAME, jobRepository)
            .start(popularKeywordStep(batchExecuteDtm, jobRepository, platformTransactionManager))
            .build()
    }

    @Bean
    fun popularKeywordStep(
        batchExecuteDtm: LocalDateTime,
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        return StepBuilder(STEP_NAME, jobRepository)
            .chunk<BlogSearchKeyword, PopularKeyword>(CHUNK_SIZE, platformTransactionManager)
            .reader(keywordReader())
            .processor(keywordProcessor(batchExecuteDtm))
            .writer(keywordWriter())
            .build()
    }

    @Bean
    fun keywordReader(): JpaPagingItemReader<BlogSearchKeyword> {
        keywordCountMap = mutableMapOf()
        return JpaPagingItemReaderBuilder<BlogSearchKeyword>()
            .name(READER_NAME)
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString("SELECT b FROM blog_search_keyword b WHERE b.processedDtm IS NULL")
            .build()
    }

    fun keywordCount(keyword: String) : PopularKeyword{
        var popularKeyword = keywordCountMap[keyword]

        if (popularKeyword != null) {
            keywordCountMap[keyword]!!.count++
        } else {
            // find by keyword
            popularKeyword = popularKeywordRepository.findByKeyword(keyword)
            if (popularKeyword == null) {
                popularKeyword = PopularKeyword().apply {
                    this.keyword = keyword
                    this.count = 0
                    this.createdDtm = LocalDateTime.now()
                }
            }
            // update
            popularKeyword.apply {
                this.count++
                this.modifiedDtm = LocalDateTime.now()
            }
            keywordCountMap[keyword] = popularKeyword
        }

        return popularKeyword
    }

    @Bean
    fun keywordProcessor(processedDtm: LocalDateTime): ItemProcessor<BlogSearchKeyword, PopularKeyword> {
        return ItemProcessor { blogSearchKeyword ->
            // processed dtm update
            blogSearchKeyword.processedDtm = processedDtm
            keywordCount(blogSearchKeyword.keyword)
        }
    }

    @Bean
    fun keywordWriter(): JpaItemWriter<PopularKeyword> {
        this.keywordCountMap.clear()
        return JpaItemWriterBuilder<PopularKeyword>()
            .entityManagerFactory(entityManagerFactory)
            .build()
    }

    override fun afterJob(jobExecution: JobExecution) {
        if (jobExecution.status == BatchStatus.COMPLETED) {
            logger().info("#### FINISHED $JOB_NAME, start delete BlogSearchKeyword")
            blogSearchKeywordRepository.deleteAllByProcessedDtmIsNotNull()
            logger().info("#### FINISHED $JOB_NAME, end delete BlogSearchKeyword")
        }
    }
}
