package com.kms.blogsearch.domain.dto

import com.kms.blogsearch.domain.BlogSearchKeyword
import java.time.LocalDateTime

data class BlogSearchKeywordDto (
    val id: Long,
    val keyword: String,
    val createdDtm: LocalDateTime
) {
    companion object {
        fun of(keyword: BlogSearchKeyword): BlogSearchKeywordDto {
            return BlogSearchKeywordDto(
                keyword.id,
                keyword.keyword,
                keyword.createdDtm
            )
        }
    }
}