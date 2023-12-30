package com.kms.blogsearch.dto

import com.kms.blogsearch.domain.BlogSearchKeyword
import java.time.LocalDateTime

data class BlogSearchKeywordDto(
    val id: Long,
    val keyword: String,
    val createdDtm: LocalDateTime,
    val processedDtm: LocalDateTime?
) {
    constructor(keyword: BlogSearchKeyword) : this(
        keyword.id,
        keyword.keyword,
        keyword.createdDtm,
        keyword.processedDtm
    )

}
