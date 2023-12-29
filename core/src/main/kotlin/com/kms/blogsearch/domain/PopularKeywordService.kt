package com.kms.blogsearch.domain

import com.kms.blogsearch.dto.PopularKeywordDto
import org.springframework.stereotype.Service

@Service
class PopularKeywordService(
    val popularKeywordRepository: PopularKeywordRepository
) {
    fun findTop10PopularKeyword(): List<PopularKeywordDto> {
        val keywordList : List<PopularKeyword> = popularKeywordRepository.findTop10PopularKeyword()
        return keywordList.map {
            PopularKeywordDto.of(
                it
            )
        }
    }
}