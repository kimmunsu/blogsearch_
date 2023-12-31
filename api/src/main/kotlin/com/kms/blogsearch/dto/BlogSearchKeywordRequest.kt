package com.kms.blogsearch.dto

import org.hibernate.validator.constraints.Range

/**
 * 블로그 검색 조회 request
 */
data class BlogSearchKeywordRequest(
    @field:Range(message = "페이지는 최소 1, 최대 50 입니다.", min = 1, max = 50)
    val page: Int,
    @field:Range(message = "페이지 사이즈는 최소 1, 최대 50 입니다.", min = 1, max = 50)
    val size: Int
)
