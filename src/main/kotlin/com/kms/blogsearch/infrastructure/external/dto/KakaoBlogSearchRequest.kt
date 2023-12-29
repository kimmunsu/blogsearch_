package com.kms.blogsearch.infrastructure.external.dto

import com.kms.blogsearch.enums.SortType
import com.kms.blogsearch.web.BlogSearchRequest

data class KakaoBlogSearchRequest(
    val query: String,
    val sort: SortType,
    val page: Int,
    val size: Int
) {
    companion object {
        fun of(queryRequest: BlogSearchRequest): KakaoBlogSearchRequest {
            return KakaoBlogSearchRequest(
                queryRequest.keyword,
                queryRequest.sorting,
                queryRequest.page,
                queryRequest.size
            )
        }
    }
}
