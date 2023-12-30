package com.kms.blogsearch.application

import com.kms.blogsearch.domain.BlogSearchKeywordService
import com.kms.blogsearch.dto.BlogSearchKeywordDto
import com.kms.blogsearch.dto.BlogSearchKeywordRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

/**
 * 블로그 키워드 검색 usecase
 */
@Service
class BlogSearchKeywordUseCase(
    private val blogSearchKeywordService: BlogSearchKeywordService
) {
    /**
     * 검색시 사용되었던 keyword 페이징 조회
     * page 통일성을 위해 api 는 FE 가 신경쓰지 않도록 page 1부터 시작하나
     * DB 조회를 하는 도메인에서는 0부터 시작하기에 여기서 1을 차감하고 조회
     */
    fun findBlogSearchKeyword(blogSearchKeywordRequest: BlogSearchKeywordRequest): Page<BlogSearchKeywordDto> {
        return PageRequest.of(
            blogSearchKeywordRequest.page - 1,
            blogSearchKeywordRequest.size - 1
        ).let {
            blogSearchKeywordService.findAllWithPaging(it)
        }
    }
}
