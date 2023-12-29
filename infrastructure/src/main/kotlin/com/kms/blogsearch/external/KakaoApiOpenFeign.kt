package com.kms.blogsearch.external

import com.kms.blogsearch.external.dto.KakaoBlogSearchRequest
import com.kms.blogsearch.external.dto.KakaoBlogSearchResponse
import com.kms.blogsearch.external.exception.KakaoApiExceptionDecoder
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
