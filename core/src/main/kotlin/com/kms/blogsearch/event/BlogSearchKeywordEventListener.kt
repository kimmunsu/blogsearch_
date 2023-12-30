package com.kms.blogsearch.event

import com.kms.blogsearch.domain.BlogSearchKeywordService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class BlogSearchKeywordEventListener(
    private val blogSearchKeywordService: BlogSearchKeywordService
) {

    /**
     * 블로그 keyword 검색 event 리스너
     * 해당 이벤트 발생시 검색 키워드 저장 수행
     * async 를 통해 비동기 처리
     */
    @EventListener
    @Async
    fun addKeywordByEvent(event: BlogSearchByKeywordEvent) {
        blogSearchKeywordService.save(event.keyword)
    }
}
