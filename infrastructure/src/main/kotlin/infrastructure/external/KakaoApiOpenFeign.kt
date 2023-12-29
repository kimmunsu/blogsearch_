package infrastructure.external

import infrastructure.external.dto.KakaoBlogSearchRequest
import infrastructure.external.dto.KakaoBlogSearchResponse
import infrastructure.external.exception.KakaoApiExceptionDecoder
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "kakaoApiOpenFeign",
    url = "\${api.kakao.api-url}",
    configuration = [KakaoApiFeignConfig::class, KakaoApiExceptionDecoder::class],
)
interface KakaoApiOpenFeign {

    @GetMapping("/v2/search/blog")
    fun search(@SpringQueryMap request: KakaoBlogSearchRequest): KakaoBlogSearchResponse
}
