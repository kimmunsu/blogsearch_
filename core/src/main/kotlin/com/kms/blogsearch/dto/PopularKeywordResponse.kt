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
    constructor(dto: PopularKeywordDto) : this(dto.keyword, dto.count)
}
