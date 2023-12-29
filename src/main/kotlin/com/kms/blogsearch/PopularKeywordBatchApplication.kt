package com.kms.blogsearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@Configuration
@SpringBootApplication
class PopularKeywordBatchApplication

fun main(args: Array<String>) {
    runApplication<PopularKeywordBatchApplication>(*args)
}