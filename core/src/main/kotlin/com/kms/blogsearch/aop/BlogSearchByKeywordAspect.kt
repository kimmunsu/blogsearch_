package com.kms.blogsearch.aop

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
    @Before("execution(* com.kms.blogsearch.domain.BlogSearchKeywordService.save(..)) && args(keyword)")
    fun publishKeywordEvent(keyword: String) {
        eventPublisher.publishEvent(BlogSearchByKeywordEvent(keyword))
    }
}