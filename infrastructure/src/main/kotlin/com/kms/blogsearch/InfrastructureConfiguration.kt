package com.kms.blogsearch

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

/**
 * infra module application properties load config
 */
@Configuration
@EnableFeignClients
@PropertySource(
    ignoreResourceNotFound = true,
    value = ["classpath:application-infra.yml"],
    factory = InfraPropertySourceFactory::class)
class InfrastructureConfiguration