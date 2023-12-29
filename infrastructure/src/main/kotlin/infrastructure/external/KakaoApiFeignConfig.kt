package infrastructure.external

import feign.Logger
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class KakaoApiFeignConfig {

    @Bean("kakaoRequestInterceptor")
    fun kakaoRequestInterceptor(@Value("\${api.kakao.application-key}") kakaoApiKey: String): RequestInterceptor {
        return RequestInterceptor { requestTemplate: RequestTemplate ->
            requestTemplate.header(
                "Authorization",
                kakaoApiKey
            )
        }
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }
}
