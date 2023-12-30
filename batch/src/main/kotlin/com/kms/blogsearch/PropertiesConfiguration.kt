package com.kms.blogsearch

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

/**
 * batch module application properties load config
 */
@Configuration
@PropertySource(
    ignoreResourceNotFound = true,
    value = ["classpath:application-batch.yml"],
    factory = BatchPropertySourceFactory::class)
class PropertiesConfiguration