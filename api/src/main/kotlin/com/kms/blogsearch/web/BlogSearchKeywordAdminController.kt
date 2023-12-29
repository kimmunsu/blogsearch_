package com.kms.blogsearch.web

import com.kms.blogsearch.application.BlogSearchKeywordUseCase
import com.kms.blogsearch.dto.BlogSearchKeywordDto
import com.kms.blogsearch.dto.BlogSearchKeywordRequest
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * event 방식을 통해 검색 키워드가 잘 저장되고, batch 를 통해 counting 및 삭제가 잘되는지 확인하기 위한 controller (test 를 용이하게 하기 위해)
 */
@RestController
@RequestMapping("/admin/blog")
class BlogSearchKeywordAdminController(
    val blogSearchKeywordUseCase: BlogSearchKeywordUseCase
) {

    @Operation(description = "search blog with pagination")
    @GetMapping
    fun findBlogSearchKeyword(blogSearchKeywordRequest: BlogSearchKeywordRequest): Page<BlogSearchKeywordDto> {
        return blogSearchKeywordUseCase.findBlogSearchKeyword(blogSearchKeywordRequest)
    }
}
