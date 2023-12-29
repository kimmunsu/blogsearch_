package com.kms.blogsearch.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class ErrorCode(val status: HttpStatusCode, val message: String) {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 입니다. 개발팀 문의주세요."),
    BAD_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청입니다, 입력값을 확인해주세요."),
    EXTERNAL_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API 서버 예외가 발생하였습니다."),

}