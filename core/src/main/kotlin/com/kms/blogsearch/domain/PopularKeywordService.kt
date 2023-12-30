package com.kms.blogsearch.domain

import com.kms.blogsearch.dto.PopularKeywordDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 인기 검색어 service
 */
@Service
class PopularKeywordService(
    val popularKeywordRepository: PopularKeywordRepository
) {

    /**
     * 인기 검색어 상위 10개 조회
     */
    @Transactional(readOnly = true)
    fun findTop10PopularKeyword(): List<PopularKeywordDto> {
        return popularKeywordRepository
            .findTop10ByOrderByCountDesc()
            .map(::PopularKeywordDto)
    }
}
