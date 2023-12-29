package com.kms.blogsearch.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface BlogSearchKeywordRepository {
    fun save(blogSearchKeyword: BlogSearchKeyword)
    fun findAllWithPaging(request: PageRequest): Page<BlogSearchKeyword>
}
