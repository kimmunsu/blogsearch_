package com.kms.blogsearch.exception

data class KakaoApiExceptionResponse(
    val errorType: String,
    val message: String
)