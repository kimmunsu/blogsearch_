package com.kms.blogsearch.dto

/**
 * 블로그 검색 결과 - page 객체
 */
data class BlogPageDto(
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean,
    val documents: List<BlogDto>
)
