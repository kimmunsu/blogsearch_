package com.kms.blogsearch.web

import com.kms.blogsearch.domain.dto.PopularKeywordDto

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
