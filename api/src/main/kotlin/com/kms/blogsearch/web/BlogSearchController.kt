package com.kms.blogsearch.web

import com.kms.blogsearch.application.BlogSearchFacade
import com.kms.blogsearch.application.PopularKyewordUseCase
import com.kms.blogsearch.dto.BlogResponse
import com.kms.blogsearch.dto.BlogSearchRequest
import com.kms.blogsearch.dto.BlogSearchRequestDto
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
    val blogSearchFacade: BlogSearchFacade,
    val popularKeywordUseCase: PopularKyewordUseCase
) {

    @Operation(description = "search blog with pagination")
    @GetMapping
    fun searchBlog(@Valid @ModelAttribute request: BlogSearchRequest): Page<BlogResponse> {
        return blogSearchFacade.searchBlog(BlogSearchRequestDto.of(request))
    }

    @Operation(description = "find top 10 popular keyword")
    @GetMapping("/popular-keyword")
    fun findTop10PopularKeyword(): List<PopularKeywordResponse> {
        return popularKeywordUseCase.findTop10PopularKeyword()
    }

}