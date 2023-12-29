package com.kms.blogsearch.dto

import com.kms.blogsearch.domain.BlogSearchKeyword
import java.time.LocalDateTime

data class BlogSearchKeywordDto(
    val id: Long,
    val keyword: String,
    val createdDtm: LocalDateTime
) {
    constructor(keyword: BlogSearchKeyword) : this(
        keyword.id,
        keyword.keyword,
        keyword.createdDtm
    )

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
