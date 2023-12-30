package com.kms.blogsearch

import com.kms.blogsearch.domain.BlogSearchKeyword
import com.kms.blogsearch.domain.PopularKeyword
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
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
    val keywordCountManager: KeywordCountManager,
    val entityManagerFactory: EntityManagerFactory
) {

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
            .start(popularKeywordStep(batchExecuteDtm.toString(), jobRepository, platformTransactionManager))
            .build()
    }

    @Bean
    @JobScope
    fun popularKeywordStep(
        @Value("#{jobParameters[EXECUTE_DTM]}") batchExecuteDtm: String,
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        return StepBuilder(STEP_NAME, jobRepository)
            .chunk<BlogSearchKeyword, PopularKeyword>(CHUNK_SIZE, platformTransactionManager)
            .reader(keywordReader())
            .processor(keywordProcessor())
            .writer(keywordWriter())
            .build()
    }

    @Bean
    fun keywordReader(): JpaPagingItemReader<BlogSearchKeyword> {
        return JpaPagingItemReaderBuilder<BlogSearchKeyword>()
            .name(READER_NAME)
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString("SELECT b FROM blog_search_keyword b WHERE b.processedDtm IS NULL")
            .build()
    }

    @Bean
    fun keywordProcessor(): ItemProcessor<BlogSearchKeyword, PopularKeyword> {

        return ItemProcessor { blogSearchKeyword ->
            // processed dtm update
            blogSearchKeyword.processedDtm = LocalDateTime.now()
            blogSearchKeywordRepository.save(blogSearchKeyword)
            val popularKeywrod = keywordCountManager.keywordCount(blogSearchKeyword.keyword)
            popularKeywrod
        }
    }

    @Bean
    fun keywordWriter(): JpaItemWriter<PopularKeyword> {
        val builder = JpaItemWriterBuilder<PopularKeyword>().entityManagerFactory(entityManagerFactory).build()
        builder.afterPropertiesSet()
        return builder
    }
}
