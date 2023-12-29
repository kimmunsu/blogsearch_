package com.kms.blogsearch.application

import com.kms.blogsearch.domain.BlogSearchKeywordService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class BlogSearchKeywordEventListener(
    val blogSearchKeywordService: BlogSearchKeywordService
) {
    @EventListener
    fun addKeywordCountByEvent(event: BlogSearchByKeywordEvent) {
        blogSearchKeywordService.save(event.keyword)
    }
}