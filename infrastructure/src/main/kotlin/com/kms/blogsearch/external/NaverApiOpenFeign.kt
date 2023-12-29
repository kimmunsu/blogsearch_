package com.kms.blogsearch.external

import com.kms.blogsearch.external.dto.NaverBlogSearchRequest
import com.kms.blogsearch.external.dto.NaverBlogSearchResponse
import com.kms.blogsearch.external.exception.NaverApiExceptionDecoder
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "naverApiOpenFeign",
    url = "\${api.naver.api-url}",
    configuration = [NaverApiFeignConfig::class, NaverApiExceptionDecoder::class],
)
interface NaverApiOpenFeign {

    @GetMapping("/v1/search/blog.json")
    fun search(@SpringQueryMap request: NaverBlogSearchRequest): NaverBlogSearchResponse
}
