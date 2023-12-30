package com.kms.blogsearch.execute

import com.kms.blogsearch.logger
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

/**
 * 인기 검색어 배치 실행 서비스 (job launcher, schedule)
 */
@Service
class PopularKeywordBatchExecuteService(
    private val jobLauncher: JobLauncher,
    private val popularKeywordJob: Job) {

    @Scheduled(cron = "0 * * * * *")
    fun execute() {
        jobLauncher.run(popularKeywordJob, JobParameters())
        logger().info("### EXECUTED JOB")
    }

}