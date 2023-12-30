package com.kms.blogsearch

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@SpringBootApplication
@EnableScheduling
class PopularKeywordBatchApplication

fun main(args: Array<String>) {
    runApplication<PopularKeywordBatchApplication>(*args)
}

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!
