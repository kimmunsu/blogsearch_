package com.kms.blogsearch.execute

import com.kms.blogsearch.KeywordCountManager
import com.kms.blogsearch.logger
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * 인기 검색어 배치 실행 서비스 (job launcher, schedule)
 */
@Component
class PopularKeywordBatchExecuteScheduler(
    private val jobLauncher: JobLauncher,
    private val popularKeywordJob: Job,
    private val keywordCountManager: KeywordCountManager
) {

    @Scheduled(cron = "0 * * * * *")
    fun execute() {
        JobParametersBuilder().addLocalDateTime("EXECUTE_DTM", LocalDateTime.now()).toJobParameters().run {
            jobLauncher.run(popularKeywordJob, this)
        }
        keywordCountManager.clear()

        logger().info("### EXECUTED JOB")
    }

}