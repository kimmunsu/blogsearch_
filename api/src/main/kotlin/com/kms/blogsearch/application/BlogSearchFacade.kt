package com.kms.blogsearch.application

import com.kms.blogsearch.domain.BlogSearchKeywordExternalRepository
import com.kms.blogsearch.dto.BlogResponse
import com.kms.blogsearch.dto.BlogSearchRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BlogSearchFacade(
    private val blogSearchKeywordExternalRepository: BlogSearchKeywordExternalRepository
) {
    fun searchBlog(request: BlogSearchRequest): Page<BlogResponse> {
        return blogSearchKeywordExternalRepository.searchBlog(request.toDto()).let { searchResult ->
            PageImpl(
                searchResult.documents.map { BlogResponse.of(it) },
                PageRequest.of(request.page, request.size),
                searchResult.totalCount.toLong()
            )
        }
    }
}
