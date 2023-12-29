package com.kms.blogsearch.external

import feign.Logger
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class NaverApiFeignConfig {

    @Bean("naverRequestInterceptor")
    fun naverRequestInterceptor(
        @Value("\${api.naver.naver-client-id}") naverClientId: String,
        @Value("\${api.naver.naver-client-secret}") naverClientSecret: String
    ): RequestInterceptor {
        return RequestInterceptor { requestTemplate: RequestTemplate ->
            requestTemplate.header("X-Naver-Client-Id", naverClientId)
            requestTemplate.header("X-Naver-Client-Secret", naverClientSecret)
        }
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }
}
