package com.kms.blogsearch.web

import com.kms.blogsearch.application.BlogSearchFacade
import com.kms.blogsearch.application.PopularKeywordUseCase
import com.kms.blogsearch.dto.BlogResponse
import com.kms.blogsearch.dto.BlogSearchRequest
import com.kms.blogsearch.dto.PopularKeywordResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/blog")
class BlogSearchController(
    private val blogSearchFacade: BlogSearchFacade,
    private val popularKeywordUseCase: PopularKeywordUseCase
) {

    @Operation(description = "search blog with pagination")
    @GetMapping
    fun searchBlog(@Valid @ModelAttribute request: BlogSearchRequest): Page<BlogResponse> {
        return blogSearchFacade.searchBlog(request)
    }

    /**
     * 인기 검색어 조회
     */
    @Operation(description = "find top 10 popular keyword")
    @GetMapping("/popular-keyword")
    fun findTop10PopularKeyword(): List<PopularKeywordResponse> {
        return popularKeywordUseCase.findTop10PopularKeyword()
    }

}