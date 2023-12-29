package com.kms.blogsearch.domain

import com.kms.blogsearch.dto.PopularKeywordDto
import org.springframework.stereotype.Service

@Service
class PopularKeywordService(
    val popularKeywordRepository: PopularKeywordRepository
) {
    fun findTop10PopularKeyword(): List<PopularKeywordDto> {
        return popularKeywordRepository
            .findTop10PopularKeyword()
            .map(::PopularKeywordDto)
    }
}
