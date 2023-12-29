package com.kms.blogsearch.infrastructure.external

import com.kms.blogsearch.domain.dto.BlogPageDto
import com.kms.blogsearch.infrastructure.dto.BlogSearchRequestDto
import com.kms.blogsearch.infrastructure.dto.KakaoBlogSearchRequest
import org.springframework.stereotype.Repository

@Repository
class BlogSearchExternalAdapter(
    val kakaoApiOpenFeign: KakaoApiOpenFeign
) {

    //TODO 예외 발생시, naver blog search 이용
    fun searchBlog(request: BlogSearchRequestDto): BlogPageDto {
        return BlogPageDto.of(kakaoApiOpenFeign.search(KakaoBlogSearchRequest.of(request)))
    }

}