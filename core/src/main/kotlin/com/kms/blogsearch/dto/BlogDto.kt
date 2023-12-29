package com.kms.blogsearch.dto


import com.kms.blogsearch.domain.dto.Document

data class BlogDto(
    val blogname: String,
    val title: String,
    val contents: String,
    val url: String,
    val datetime: String,
    val thumbnail: String
)
