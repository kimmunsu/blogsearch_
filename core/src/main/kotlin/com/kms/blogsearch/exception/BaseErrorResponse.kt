package com.kms.blogsearch.exception

data class BaseErrorResponse(
    val status: Int,
    val code: String,
    val message: String
)