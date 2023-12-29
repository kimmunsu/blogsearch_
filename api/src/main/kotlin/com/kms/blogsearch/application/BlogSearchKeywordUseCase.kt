package com.kms.blogsearch.application

import com.kms.blogsearch.domain.BlogSearchKeywordService
import com.kms.blogsearch.dto.BlogSearchKeywordDto
import com.kms.blogsearch.dto.BlogSearchKeywordRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BlogSearchKeywordUseCase(
    private val blogSearchKeywordService: BlogSearchKeywordService
) {
    fun findBlogSearchKeyword(blogSearchKeywordRequest: BlogSearchKeywordRequest): Page<BlogSearchKeywordDto> {
        // pagination 에 대하여 FE 와 통일성을 위하여 page를 1부터 시작합니다.
        return PageRequest.of(
            blogSearchKeywordRequest.page - 1,
            blogSearchKeywordRequest.size - 1
        ).let {
            blogSearchKeywordService.findAllWithPaging(it)
        }
    }
}
