package com.kms.blogsearch.infrastructure

import com.kms.blogsearch.domain.PopularKeyword
import com.kms.blogsearch.domain.PopularKeywordRepository
import org.springframework.stereotype.Repository

@Repository
class PopularKeywordPersistenceAdapter(
    val popularKeywordSpringDataRepository: PopularKeywordSpringDataRepository
): PopularKeywordRepository {
    override fun findTop10PopularKeyword(): List<PopularKeyword> {
        return popularKeywordSpringDataRepository.findTop10ByOrderByCountDesc()
    }
}