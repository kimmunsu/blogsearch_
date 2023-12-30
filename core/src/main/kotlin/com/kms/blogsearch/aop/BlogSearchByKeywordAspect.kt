package com.kms.blogsearch.aop

import com.kms.blogsearch.dto.BlogSearchRequestDto
import com.kms.blogsearch.event.BlogSearchByKeywordEvent
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

/**
 * keyword counting 은 blog 조회 비즈니스 로직과 별개이고, 횡단 가능한 요구조건이기에 AOP 처리
 */
@Aspect
@Component
class BlogSearchByKeywordAspect(val eventPublisher: ApplicationEventPublisher) {

    /**
     * 블로그 검색시 사용된 request 의 keyword 를 담은 event 발행
     */
    @Before("execution(* com.kms.blogsearch.domain.BlogSearchKeywordExternalRepository.searchBlog(..)) && args(request)")
    fun publishKeywordEvent(request: BlogSearchRequestDto) {
        eventPublisher.publishEvent(BlogSearchByKeywordEvent(request.keyword))
    }
}
