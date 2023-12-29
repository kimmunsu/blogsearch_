package com.kms.blogsearch.exception

import feign.Response

data class ExternalOpenFeignApiException(
    override val message: String,
    val response: Response?
): RuntimeException()