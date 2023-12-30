package com.kms.blogsearch.exception

/**
 * 공통 exception
 */
data class BaseException(val errorCode: ErrorCode) : RuntimeException()
