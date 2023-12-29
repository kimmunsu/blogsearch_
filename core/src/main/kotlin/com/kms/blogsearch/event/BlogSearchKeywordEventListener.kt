package com.kms.blogsearch.event

import com.kms.blogsearch.domain.BlogSearchKeywordService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class BlogSearchKeywordEventListener(
    private val blogSearchKeywordService: BlogSearchKeywordService
) {
    @EventListener
    @Async
    fun addKeywordCountByEvent(event: BlogSearchByKeywordEvent) {
        blogSearchKeywordService.save(event.keyword)
    }
}
