package com.kms.blogsearch.exception

data class BaseException(val errorCode: ErrorCode): RuntimeException()