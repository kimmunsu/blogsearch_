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
 * @property keyword 검색어
 * @property count 조회수
 * @property createdDtm 생성일시 (일단위, 혹은 수시간 단위로 과거 검색어 제거 등의 로직에 사용을 위해)
 * @property modifiedDtm 수정일시 (일단위, 혹은 수시간 단위로 과거 검색어 제거 등의 로직에 사용을 위해)
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
