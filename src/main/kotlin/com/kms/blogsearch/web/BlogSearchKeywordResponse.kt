package com.kms.blogsearch.web

import com.kms.blogsearch.domain.dto.BlogSearchKeywordDto
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