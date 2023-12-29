package com.kms.blogsearch.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.apache.commons.lang3.StringUtils
import java.time.LocalDateTime

/**
 * 인기 검색어
 * batch 를 통해 count
 */
@Entity
class PopularKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
    var keyword: String = StringUtils.EMPTY
    var count: Int = 0
    var createdDtm: LocalDateTime = LocalDateTime.now()
    var modifiedDtm: LocalDateTime = LocalDateTime.MIN
}
