package com.kms.blogsearch.domain.dto

import com.kms.blogsearch.infrastructure.dto.Document

data class BlogDto(
    val blogname: String,
    val title: String,
    val contents: String,
    val url: String,
    val datetime: String,
    val thumbnail: String
) {
    companion object {
        fun of(document: Document): BlogDto {
            return BlogDto(
                document.blogname,
                document.title,
                document.contents,
                document.url,
                document.datetime,
                document.thumbnail
            )
        }
    }
}
