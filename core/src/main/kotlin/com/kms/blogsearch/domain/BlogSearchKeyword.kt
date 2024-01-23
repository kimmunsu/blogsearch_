package com.kms.blogsearch.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

/**
 * 검색된 키워드를 저장하기 위한 entity. (검색 키워드의 counting 처리는 batch 에서 수행)
 */
@Entity(name = "blog_search_keyword")
class BlogSearchKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
    @Column(name = "keyword")
    var keyword: String = ""
    @Column(name = "created_dtm", nullable = false)
    var createdDtm: LocalDateTime = LocalDateTime.MIN
    @Column(name = "processed_dtm")
    var processedDtm: LocalDateTime? = null
}
