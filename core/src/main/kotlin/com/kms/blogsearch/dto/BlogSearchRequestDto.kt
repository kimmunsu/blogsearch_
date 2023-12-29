package com.kms.blogsearch.dto

data class BlogSearchRequestDto(
    val keyword: String,
    val sorting: SortType = SortType.ACCURACY,
    val page: Int,
    val size: Int
)
