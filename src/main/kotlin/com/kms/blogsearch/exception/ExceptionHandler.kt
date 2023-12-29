package com.kms.blogsearch.exception

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.kms.blogsearch.infrastructure.external.exception.KakaoApiExceptionResponse
import com.kms.blogsearch.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

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

    //@Valid exception handle
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(e: MethodArgumentNotValidException): ResponseEntity<Map<String, BaseErrorResponse>> {
        val result = mutableMapOf<String, BaseErrorResponse>()
        e.bindingResult.allErrors.forEach {
            result.put(
                (it as FieldError).field,
                BaseErrorResponse(
                    ErrorCode.BAD_PARAMETER.status.value(),
                    ErrorCode.BAD_PARAMETER.name,
                    it.defaultMessage ?: ErrorCode.BAD_PARAMETER.message
                )
            )
        }

        return ResponseEntity.status(ErrorCode.BAD_PARAMETER.status)
            .body(
                result
            )

    }

    // external api exception handle
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

    //common
    @ExceptionHandler(Exception::class)
    fun exceptionHandle(e: Exception): ResponseEntity<BaseErrorResponse> {
        logger().error(e.message)
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