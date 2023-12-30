package com.kms.blogsearch.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import redis.embedded.RedisServer

/**
 * 과제를 위한 embedded redis config
 */
@Configuration
class EmbeddedRedisServerConfig: RedisServerConfig {
    @Value("\${spring.redis.host}")
    private var host: String = StringUtils.EMPTY
    @Value("\${spring.redis.port}")
    private var redisPort: Int = 0

    lateinit var redisServer: RedisServer

    @PostConstruct
    fun redisServer() {
        this.redisServer = RedisServer(redisPort)
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
    }

    override fun getPort(): Int {
        return redisPort
    }

    override fun getHost(): String {
        return host
    }

}