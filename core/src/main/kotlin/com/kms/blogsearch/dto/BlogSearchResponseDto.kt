package com.kms.blogsearch.dto

data class BlogSearchResponseDto(
    val blogname: String,
    val title: String,
    val contents: String,
    val url: String,
    val datetime: String,
    val thumbnail: String
)