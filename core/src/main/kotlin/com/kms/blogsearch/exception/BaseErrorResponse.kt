package com.kms.blogsearch.exception

/**
 * 공통 예외 response
 */
data class BaseErrorResponse(
    val status: Int,
    val code: String,
    val message: String
)
