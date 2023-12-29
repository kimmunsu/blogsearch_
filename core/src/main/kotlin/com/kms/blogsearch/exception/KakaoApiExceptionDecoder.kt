package com.kms.blogsearch.exception

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import feign.Response
import feign.codec.ErrorDecoder

class KakaoApiExceptionDecoder: ErrorDecoder {

    val objectMapper = jacksonObjectMapper().registerKotlinModule()

    override fun decode(methodKey: String?, response: Response?): ExternalOpenFeignApiException {
        val message = response?.let {
            val exception = objectMapper.readValue(it.body().asInputStream(), KakaoApiExceptionResponse::class.java)
            exception.message
        } ?: ErrorCode.EXTERNAL_API_ERROR.message

        return ExternalOpenFeignApiException(message, response)
    }

}