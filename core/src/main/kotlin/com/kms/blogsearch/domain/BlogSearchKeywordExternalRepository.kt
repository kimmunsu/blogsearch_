package com.kms.blogsearch.domain

import com.kms.blogsearch.dto.BlogPageDto
import com.kms.blogsearch.dto.BlogSearchRequestDto

interface BlogSearchKeywordExternalRepository {
    fun searchBlog(request: BlogSearchRequestDto): BlogPageDto
}