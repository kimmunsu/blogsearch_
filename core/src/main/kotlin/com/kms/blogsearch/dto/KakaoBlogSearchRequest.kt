package com.kms.blogsearch.dto

data class KakaoBlogSearchRequest(
    val query: String,
    val sort: SortType,
    val page: Int,
    val size: Int
) {
    companion object {
        fun of(queryRequest: BlogSearchRequestDto): KakaoBlogSearchRequest {
            return KakaoBlogSearchRequest(
                queryRequest.keyword,
                queryRequest.sorting,
                queryRequest.page,
                queryRequest.size
            )
        }
    }
}
