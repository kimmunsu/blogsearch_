package com.kms.blogsearch.application

import com.kms.blogsearch.domain.BlogSearchKeywordService
import com.kms.blogsearch.domain.dto.BlogSearchKeywordDto
import com.kms.blogsearch.web.BlogSearchKeywordRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BlogSearchKeywordUseCase(
    val blogSearchKeywordService: BlogSearchKeywordService
) {

    fun findBlogSearchKeyword(blogSearchKeywordRequest: BlogSearchKeywordRequest): Page<BlogSearchKeywordDto> {
        return PageRequest.of(blogSearchKeywordRequest.page - 1, blogSearchKeywordRequest.size - 1).let {
            blogSearchKeywordService.findAllWithPaging(it)
        }

    }

}