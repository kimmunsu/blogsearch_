package com.kms.blogsearch.dto

/**
 * 블로그 검색 결과
 */
data class BlogDto(
    val blogname: String,
    val title: String,
    val contents: String,
    val url: String,
    val datetime: String,
    val thumbnail: String
)
