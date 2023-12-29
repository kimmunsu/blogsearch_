package com.kms.blogsearch.dto

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
