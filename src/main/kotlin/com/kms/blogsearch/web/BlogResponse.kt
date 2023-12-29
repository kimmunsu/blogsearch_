package com.kms.blogsearch.web

import com.kms.blogsearch.domain.dto.BlogDto

data class BlogResponse(
    val blogname: String,
    val title: String,
    val contents: String,
    val url: String,
    val datetime: String,
    val thumbnail: String
) {
    companion object {
        fun of(blog: BlogDto): BlogResponse {
            return BlogResponse(
                blog.blogname,
                blog.title,
                blog.contents,
                blog.url,
                blog.datetime,
                blog.thumbnail
            )
        }
    }
}