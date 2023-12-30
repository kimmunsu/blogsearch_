package com.kms.blogsearch.exception

import feign.Response

/**
 * 외부 api openfeign exception
 * 기본 message 를 들고 있으며,
 * 어떤 외부 api 에서 발생한 예외인지 추적을 위한 feign Response 를 객체로 들고 있다.
 * TODO : wrap response
 */
data class ExternalOpenFeignApiException(
    override val message: String,
    val response: Response?
) : RuntimeException()
