package com.kms.blogsearch.dto

/**
 * 인기 검색어
 * @property keyword 검색어
 * @property count 검색 수
 */
data class PopularKeywordResponse(
    val keyword: String,
    val count: Int
) {
    companion object {
        fun of(dto: PopularKeywordDto): PopularKeywordResponse {
            return PopularKeywordResponse(
                dto.keyword,
                dto.count
            )
        }
    }
}
