package com.kms.blogsearch.external.dto

import com.kms.blogsearch.dto.BlogDto
import com.kms.blogsearch.dto.BlogPageDto

data class NaverBlogSearchResponse(
    val title: String,
    val link: String,
    val description: String,
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<NaverBlogItem>
) {
    fun toDto(): BlogPageDto {
        return BlogPageDto(
            total,
            display,
            total <= display * start,
            items.map{ it.toDto() }
        )
    }
}

data class NaverBlogItem(
    val title: String,
    val link: String,
    val url: String,
    val description: String,
    val bloggername: String,
    val bloggerlink: String,
    val postdate: String
) {
    fun toDto(): BlogDto {
         return BlogDto(
             bloggername,
             title,
             description,
             url,
             postdate,
             link
         )
    }
}
