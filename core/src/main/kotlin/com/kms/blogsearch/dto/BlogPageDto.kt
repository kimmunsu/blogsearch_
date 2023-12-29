package com.kms.blogsearch.dto

data class BlogPageDto(
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean,
    val documents: List<BlogDto>
)
