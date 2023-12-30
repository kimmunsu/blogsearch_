package com.kms.blogsearch.application

import com.kms.blogsearch.domain.BlogSearchKeywordExternalRepository
import com.kms.blogsearch.dto.BlogResponse
import com.kms.blogsearch.dto.BlogSearchRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

/**
 * 블로그 조회 facade
 */
@Service
class BlogSearchFacade(
    private val blogSearchKeywordExternalRepository: BlogSearchKeywordExternalRepository
) {

    /**
     * 블로그 검색
     * 검색결과는 page 정보를 가진 객체로서, 해당 객체를 page 로 변환
     */
    fun searchBlog(request: BlogSearchRequest): Page<BlogResponse> {
        return blogSearchKeywordExternalRepository.searchBlog(request.toDto()).let { searchResult ->
            PageImpl(
                searchResult.documents.map { BlogResponse(it) },
                PageRequest.of(request.page, request.size),
                searchResult.totalCount.toLong()
            )
        }
    }
}
