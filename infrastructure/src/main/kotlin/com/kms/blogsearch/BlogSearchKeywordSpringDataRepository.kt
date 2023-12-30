package com.kms.blogsearch

import com.kms.blogsearch.domain.BlogSearchKeyword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlogSearchKeywordSpringDataRepository : JpaRepository<BlogSearchKeyword, Long> {
    fun deleteAllByProcessedDtmIsNotNull()
}
