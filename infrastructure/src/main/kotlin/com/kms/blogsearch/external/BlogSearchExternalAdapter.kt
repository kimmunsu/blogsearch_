package com.kms.blogsearch.external

import com.kms.blogsearch.domain.BlogSearchKeywordExternalRepository
import com.kms.blogsearch.dto.BlogPageDto
import com.kms.blogsearch.dto.BlogSearchRequestDto
import com.kms.blogsearch.external.dto.KakaoBlogSearchRequest
import com.kms.blogsearch.external.dto.NaverBlogSearchRequest
import org.springframework.stereotype.Repository

@Repository
class BlogSearchExternalAdapter(
    private val kakaoApiOpenFeign: KakaoApiOpenFeign,
    private val naverApiOpenFeign: NaverApiOpenFeign
): BlogSearchKeywordExternalRepository {

    /**
     * 블로그 검색
     * 우선 kakao api 검색
     * 예외발생시 naver api 검색
     */
    override fun searchBlog(request: BlogSearchRequestDto): BlogPageDto {
        return try {
                kakaoApiOpenFeign.search(KakaoBlogSearchRequest(request)).toDto()
            } catch (e: Exception) {
                naverApiOpenFeign.search(NaverBlogSearchRequest(request)).toDto()
            }
    }
}
