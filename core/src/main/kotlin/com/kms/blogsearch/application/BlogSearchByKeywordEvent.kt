package com.kms.blogsearch.application

import org.springframework.context.ApplicationEvent

class BlogSearchByKeywordEvent(
    val keyword: String,
    source: Any
): ApplicationEvent(source)