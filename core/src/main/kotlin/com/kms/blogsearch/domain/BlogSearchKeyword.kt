package com.kms.blogsearch.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

/**
 * 검색된 키워드를 저장하기 위한 entity. (검색 키워드의 counting 처리는 batch 에서 수행)
 */
@Entity
class BlogSearchKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
    var keyword: String = ""
    var createdDtm: LocalDateTime = LocalDateTime.MIN
    var processedDtm : LocalDateTime? = null
}
