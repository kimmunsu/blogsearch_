package com.kms.blogsearch.dto

import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range

/**
 * 블로그 검색 request
 */
data class BlogSearchRequest(
    @field:NotNull(message = "키워드는 반드시 입력값이 있어야 합니다.")
    val keyword: String,
    val sorting: SortType = SortType.ACCURACY,
    @field:Range(message = "페이지는 최소 1, 최대 50 입니다.", min = 1, max = 50)
    val page: Int,
    @field:Range(message = "페이지 사이즈는 최소 1, 최대 50 입니다.", min = 1, max = 50)
    val size: Int
) {
    fun toDto(): BlogSearchRequestDto {
        return BlogSearchRequestDto(
            keyword,
            sorting,
            page,
            size
        )
    }
}
