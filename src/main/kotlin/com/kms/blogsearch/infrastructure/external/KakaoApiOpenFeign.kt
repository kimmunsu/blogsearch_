package com.kms.blogsearch.infrastructure.external

import com.kms.blogsearch.infrastructure.external.dto.KakaoBlogSearchRequest
import com.kms.blogsearch.infrastructure.external.dto.KakaoBlogSearchResponse
import com.kms.blogsearch.infrastructure.external.exception.KakaoApiExceptionDecoder
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name="kakaoApiOpenFeign",
    url = "https://dapi.kakao.com",
    configuration = [KakaoApiFeignConfig::class, KakaoApiExceptionDecoder::class],
)
interface KakaoApiOpenFeign {

    @GetMapping("/v2/search/blog")
    fun search(@SpringQueryMap request: KakaoBlogSearchRequest): KakaoBlogSearchResponse

}