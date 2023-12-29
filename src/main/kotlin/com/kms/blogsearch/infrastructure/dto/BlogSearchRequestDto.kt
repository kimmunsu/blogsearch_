package com.kms.blogsearch.infrastructure.dto

import com.kms.blogsearch.enums.SortType
import com.kms.blogsearch.web.BlogSearchRequest

data class BlogSearchRequestDto(
    val keyword: String,
    val sorting: SortType = SortType.ACCURACY,
    val page: Int,
    val size : Int
) {
    companion object {
        fun of(request: BlogSearchRequest): BlogSearchRequestDto {
            return BlogSearchRequestDto(
                request.keyword,
                request.sorting,
                request.page,
                request.size
            )
        }
    }
}