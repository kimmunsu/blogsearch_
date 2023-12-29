package com.kms.blogsearch.application

import com.kms.blogsearch.dto.BlogResponse
import com.kms.blogsearch.dto.BlogSearchRequestDto
import com.kms.blogsearch.infrastructure.external.BlogSearchExternalAdapter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BlogSearchFacade(
    private val blogSearchExternalAdapter: BlogSearchExternalAdapter
) {
//    private lateinit var eventPublisher: ApplicationEventPublisher

    fun searchBlog(request: BlogSearchRequestDto): Page<BlogResponse> {
        publish(request)
        return blogSearchExternalAdapter.searchBlog(request).let { searchResult ->
            PageImpl(
                searchResult.documents.map { BlogResponse.of(it) },
                PageRequest.of(request.page, request.size),
                searchResult.totalCount.toLong()
            )
        }
    }

    //TODO API keyword counting 은 blog 조회 비즈니스 로직과 별개이고, 횡단 가능한 요구조건이기에 AOP 처리
    private fun publish(request: BlogSearchRequestDto) {
//        eventPublisher.publishEvent(BlogSearchByKeywordEvent(request.keyword, this))
        //TODO logger().info("### published BlogSearchByKeywordEvent, keyword : $request.keyword")
    }
}