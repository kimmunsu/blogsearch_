package com.kms.blogsearch.config

interface RedisServerConfig {
    fun getPort(): Int
    fun getHost(): String
}