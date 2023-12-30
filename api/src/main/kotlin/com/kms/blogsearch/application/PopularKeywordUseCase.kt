package com.kms.blogsearch.application

import com.kms.blogsearch.domain.PopularKeywordService
import com.kms.blogsearch.dto.PopularKeywordResponse
import org.springframework.stereotype.Service

/**
 * 인기 검색어 usecase
 */
@Service
class PopularKeywordUseCase(
    private val popularKeywordService: PopularKeywordService
) {

    /**
     * 인기 검색어 상위 10개 조회
     */
    fun findTop10PopularKeyword(): List<PopularKeywordResponse> {
        return popularKeywordService
            .findTop10PopularKeyword()
            .map(::PopularKeywordResponse)
    }
}
