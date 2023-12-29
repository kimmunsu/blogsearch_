package com.kms.blogsearch.domain.dto

import com.kms.blogsearch.infrastructure.dto.KakaoBlogSearchResponse

data class BlogPageDto (
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean,
    val documents: List<BlogDto>
) {
    companion object {
        fun of(searchResult: KakaoBlogSearchResponse): BlogPageDto {
            return BlogPageDto(
                searchResult.totalCount,
                searchResult.pageableCount,
                searchResult.isEnd,
                searchResult.documents.map {
                    BlogDto.of(it)
                }
            )
        }
    }
}