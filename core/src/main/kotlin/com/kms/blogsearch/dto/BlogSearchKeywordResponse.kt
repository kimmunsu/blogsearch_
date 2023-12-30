package com.kms.blogsearch.dto

import java.time.LocalDateTime

data class BlogSearchKeywordResponse(
    val id: Long,
    val keyword: String,
    val createdDtm: LocalDateTime
) {

    constructor(dto: BlogSearchKeywordDto): this(
        dto.id,
        dto.keyword,
        dto.createdDtm
    )

}
