package infrastructure.external.dto

import com.kms.blogsearch.dto.BlogSearchRequestDto

data class KakaoBlogSearchRequest(
    val query: String,
    val sort: String,
    val page: Int,
    val size: Int
) {

    constructor(queryRequest: BlogSearchRequestDto): this (
        queryRequest.keyword,
        queryRequest.sorting.kakao,
        queryRequest.page,
        queryRequest.size
    )
}
