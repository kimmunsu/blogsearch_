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

@Configuration
class PopularKeywordJobConfiguration(
    val blogSearchKeywordRepository: BlogSearchKeywordSpringDataRepository,
    val popularKeywordRepository: PopularKeywordSpringDataRepository,
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

    @Bean
    fun keywordProcessor(processedDtm: LocalDateTime): ItemProcessor<BlogSearchKeyword, PopularKeyword> {
        return ItemProcessor { blogSearchKeyword ->
            var popularKeyword = keywordCountMap[blogSearchKeyword.keyword]

            if (popularKeyword != null) {
                keywordCountMap[blogSearchKeyword.keyword]!!.count++
            } else {
                // find by keyword
                popularKeyword = popularKeywordRepository.findByKeyword(blogSearchKeyword.keyword)
                if (popularKeyword == null) {
                    popularKeyword = PopularKeyword().apply {
                        this.keyword = blogSearchKeyword.keyword
                        this.count = 0
                        this.createdDtm = LocalDateTime.now()
                    }
                }

                // update
                popularKeyword.apply {
                    this.count++
                    this.modifiedDtm = LocalDateTime.now()
                }

                keywordCountMap[blogSearchKeyword.keyword] = popularKeyword
            }

            // processed dtm update
            blogSearchKeyword.processedDtm = processedDtm
            popularKeyword
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
