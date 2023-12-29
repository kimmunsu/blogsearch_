package com.kms.blogsearch.dto

data class BlogSearchRequestDto(
    val keyword: String,
    val sorting: SortType = SortType.ACCURACY,
    val page: Int,
    val size : Int
) {
    companion object {
        fun of(request: BlogSearchRequest): BlogSearchRequestDto {
            return BlogSearchRequestDto(
                request.keyword,
                request.sorting,
                request.page,
                request.size
            )
        }
    }
}