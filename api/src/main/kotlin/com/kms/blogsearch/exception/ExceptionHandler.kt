package com.kms.blogsearch.exception

import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    /**
     * application 공통 exception handle
     */
    @ExceptionHandler(BaseException::class)
    fun baseExceptionHandle(e: BaseException): ResponseEntity<BaseErrorResponse> {
        return ResponseEntity.status(e.errorCode.status)
            .body(
                BaseErrorResponse(
                    e.errorCode.status.value(),
                    e.errorCode.name,
                    e.errorCode.message
                )
            )
    }

    /**
     * @Valid exception handle
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(e: MethodArgumentNotValidException): ResponseEntity<Map<String, BaseErrorResponse>> {
        val result = mutableMapOf<String, BaseErrorResponse>()
        e.bindingResult.allErrors.forEach {
            result[(it as FieldError).field] = BaseErrorResponse(
                ErrorCode.BAD_PARAMETER.status.value(),
                ErrorCode.BAD_PARAMETER.name,
                it.defaultMessage ?: ErrorCode.BAD_PARAMETER.message
            )
        }

        return ResponseEntity.status(ErrorCode.BAD_PARAMETER.status)
            .body(
                result
            )
    }

    /**
     * external api exception handle
     */
    @ExceptionHandler(ExternalOpenFeignApiException::class)
    fun externalOpenFeignApiException(e: ExternalOpenFeignApiException): ResponseEntity<BaseErrorResponse> {
        val errorCode = ErrorCode.EXTERNAL_API_ERROR
        val message = e.response?.let { response ->
            val errorMessage = e.message
            val method = response.request().httpMethod()
            val url = response.request().url()
            "$method, $url, ### external api error message : $errorMessage"
        } ?: errorCode.message

        return ResponseEntity.status(errorCode.status)
            .body(
                BaseErrorResponse(
                    errorCode.status.value(),
                    errorCode.name,
                    message
                )
            )
    }

    /**
     * 미정의 예외, server error 처리
     */
    @ExceptionHandler(Exception::class)
    fun exceptionHandle(e: Exception): ResponseEntity<BaseErrorResponse> {
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.status)
            .body(
                BaseErrorResponse(
                    ErrorCode.INTERNAL_SERVER_ERROR.status.value(),
                    ErrorCode.INTERNAL_SERVER_ERROR.name,
                    ErrorCode.INTERNAL_SERVER_ERROR.message
                )
            )
    }
}