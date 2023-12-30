package com.kms.blogsearch.dto

/**
 * 블로그 response
 */
data class BlogResponse(
    val blogname: String,
    val title: String,
    val contents: String,
    val url: String,
    val datetime: String,
    val thumbnail: String
) {

    constructor(blog: BlogDto): this(
        blog.blogname,
        blog.title,
        blog.contents,
        blog.url,
        blog.datetime,
        blog.thumbnail
    )

}
