package com.kms.blogsearch

import com.kms.blogsearch.domain.BlogSearchKeyword
import com.kms.blogsearch.domain.PopularKeyword
import com.kms.blogsearch.infrastructure.BlogSearchKeywordSpringDataRepository
import com.kms.blogsearch.infrastructure.PopularKeywordSpringDataRepository
import org.springframework.batch.core.*
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
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
    val popularKeywordRepository: PopularKeywordSpringDataRepository
): JobExecutionListener {

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
            .incrementer(RunIdIncrementer())
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
        blogSearchKeywordRepository.findByProcessedDtmIsNull()
        return JpaPagingItemReaderBuilder<BlogSearchKeyword>()
            .name(READER_NAME)
            .pageSize(CHUNK_SIZE)
            .queryString("SELECT k FROM blocksearchkeyword k WHERE processedDtm IS NULL")
            .build()
    }

    @Bean
    fun keywordProcessor(processedDtm: LocalDateTime): ItemProcessor<BlogSearchKeyword, PopularKeyword> {
        return ItemProcessor { blogSearchKeyword ->

            // find by keyword
            var popularKeyword = popularKeywordRepository.findByKeyword(blogSearchKeyword.keyword)
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
            }.also {
                // processed dtm update
                blogSearchKeyword.processedDtm = processedDtm
            }
        }
    }

    @Bean
    fun keywordWriter(): JpaItemWriter<PopularKeyword> {
        return JpaItemWriterBuilder<PopularKeyword>()
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