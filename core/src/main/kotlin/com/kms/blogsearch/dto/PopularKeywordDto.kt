package com.kms.blogsearch.dto

import com.kms.blogsearch.domain.PopularKeyword

data class PopularKeywordDto(
    val keyword: String,
    val count: Int
) {
    companion object {
        fun of(popularKeyword: PopularKeyword): PopularKeywordDto {
            return PopularKeywordDto(
                popularKeyword.keyword,
                popularKeyword.count
            )
        }
    }
}
