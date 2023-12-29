package com.kms.blogsearch.domain.dto

data class KakaoBlogSearchResponse (
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean,
    val documents: List<Document>
)
data class Document(
    val blogname: String,
    val title: String,
    val contents: String,
    val url: String,
    val datetime: String,
    val thumbnail: String
)