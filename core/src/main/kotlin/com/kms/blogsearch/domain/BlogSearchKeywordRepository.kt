package com.kms.blogsearch.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

/**
 * 블로그 검색 keyword repository interface
 */
interface BlogSearchKeywordRepository {

    /**
     * 블로그 검색 keyword 저장
     */
    fun save(blogSearchKeyword: BlogSearchKeyword)

    /**
     * 블로그 검색 keyword 리스트 조회 - page
     */
    fun findAllWithPaging(request: PageRequest): Page<BlogSearchKeyword>
}
