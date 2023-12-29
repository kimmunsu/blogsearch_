package com.kms.blogsearch.application

import com.kms.blogsearch.domain.PopularKeywordService
import com.kms.blogsearch.dto.PopularKeywordResponse
import org.springframework.stereotype.Service

@Service
class PopularKyewordUseCase(
    val popularKeywordService: PopularKeywordService
) {
    fun findTop10PopularKeyword(): List<PopularKeywordResponse> {
        return popularKeywordService.findTop10PopularKeyword().let {
            it.map { dto ->
                PopularKeywordResponse.of(
                    dto
                )
            }
        }
    }

}