package com.kms.blogsearch.dto

import java.time.LocalDateTime

data class BlogSearchKeywordResponse(
    val id: Long,
    val keyword: String,
    val createdDtm: LocalDateTime
) {
    companion object {
        fun of(dto: BlogSearchKeywordDto): BlogSearchKeywordResponse {
            return BlogSearchKeywordResponse(
                dto.id,
                dto.keyword,
                dto.createdDtm
            )
        }
    }
}
