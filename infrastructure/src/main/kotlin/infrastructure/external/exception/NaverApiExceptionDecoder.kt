package infrastructure.external.exception

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.kms.blogsearch.exception.ErrorCode
import com.kms.blogsearch.exception.ExternalOpenFeignApiException
import feign.Response
import feign.codec.ErrorDecoder

class NaverApiExceptionDecoder : ErrorDecoder {

    private val objectMapper = jacksonObjectMapper().registerKotlinModule()

    override fun decode(methodKey: String?, response: Response?): ExternalOpenFeignApiException {
        val message = response?.let {
            objectMapper.readValue(it.body().asInputStream(), NaverApiExceptionResponse::class.java).errorMessage
        } ?: ErrorCode.EXTERNAL_API_ERROR.message

        return ExternalOpenFeignApiException(message, response)
    }
}
