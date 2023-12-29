package com.kms.blogsearch.application

import com.kms.blogsearch.infrastructure.external.BlogSearchExternalAdapter
import com.kms.blogsearch.web.BlogSearchRequest
import com.kms.blogsearch.web.BlogResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BlogSearchFacade(
    val blogSearchExternalAdapter: BlogSearchExternalAdapter
) {
    fun searchBlog(request: BlogSearchRequest): Page<BlogResponse> {
        return blogSearchExternalAdapter.searchBlog(request).let { searchResult ->
            PageImpl(
                searchResult.documents.map { BlogResponse.of(it) },
                PageRequest.of(request.page, request.size),
                searchResult.totalCount.toLong()
            )
        }
    }

}