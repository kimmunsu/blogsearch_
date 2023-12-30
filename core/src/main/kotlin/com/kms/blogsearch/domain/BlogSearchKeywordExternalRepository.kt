package com.kms.blogsearch.domain

import com.kms.blogsearch.dto.BlogPageDto
import com.kms.blogsearch.dto.BlogSearchRequestDto

/**
 * 블로그 검색 repository inteface - 외부 API 검색
 */
interface BlogSearchKeywordExternalRepository {

    fun searchBlog(request: BlogSearchRequestDto): BlogPageDto
}
