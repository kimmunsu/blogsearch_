package com.kms.blogsearch.dto

import com.kms.blogsearch.domain.PopularKeyword

/**
 * 인기 검색어 dto
 * @property keyword 검색어
 * @property count 조회수
 */
data class PopularKeywordDto(
    val keyword: String,
    val count: Int
) {
    constructor(popularKeyword: PopularKeyword) : this(popularKeyword.keyword, popularKeyword.count)
}
