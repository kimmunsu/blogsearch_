package com.kms.blogsearch.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

/**
 * batch module application properties load config
 */
@Configuration
@PropertySource(
    ignoreResourceNotFound = true,
    value = ["classpath:batch-application.yml"],
    factory = PropertySourceFactory::class)
class PropertiesConfiguration