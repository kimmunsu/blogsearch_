package com.kms.blogsearch.infrastructure.external.exception

data class KakaoApiExceptionResponse(
    val errorType: String,
    val message: String
)