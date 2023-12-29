package com.kms.blogsearch.event

import org.springframework.context.ApplicationEvent

class BlogSearchByKeywordEvent(
    val keyword: String,
    source: Any
): ApplicationEvent(source)