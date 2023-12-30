package com.kms.blogsearch

import com.kms.blogsearch.domain.PopularKeyword
import com.kms.blogsearch.domain.PopularKeywordRepository
import org.springframework.stereotype.Repository

@Repository
class PopularKeywordPersistenceAdapter(
    private val popularKeywordSpringDataRepository: PopularKeywordSpringDataRepository
) : PopularKeywordRepository {

    override fun findTop10ByOrderByCountDesc(): List<PopularKeyword> {
        return popularKeywordSpringDataRepository.findTop10ByOrderByCountDesc()
    }
}
