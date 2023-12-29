package com.kms.blogsearch.application

import com.kms.blogsearch.domain.PopularKeywordService
import com.kms.blogsearch.dto.PopularKeywordResponse
import org.springframework.stereotype.Service

@Service
class PopularKeywordUseCase(
    private val popularKeywordService: PopularKeywordService
) {
    fun findTop10PopularKeyword(): List<PopularKeywordResponse> {
        return popularKeywordService
            .findTop10PopularKeyword()
            .map(::PopularKeywordResponse)
    }
}
