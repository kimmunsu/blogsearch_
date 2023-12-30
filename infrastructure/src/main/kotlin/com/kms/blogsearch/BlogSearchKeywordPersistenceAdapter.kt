package com.kms.blogsearch

import com.kms.blogsearch.domain.BlogSearchKeyword
import com.kms.blogsearch.domain.BlogSearchKeywordRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class BlogSearchKeywordPersistenceAdapter(
    private val blogSearchKeywordSpringDataRepository: BlogSearchKeywordSpringDataRepository
) : BlogSearchKeywordRepository {

    override fun save(blogSearchKeyword: BlogSearchKeyword) {
        blogSearchKeywordSpringDataRepository.save(blogSearchKeyword)
    }

    override fun findAllWithPaging(request: PageRequest): Page<BlogSearchKeyword> {
        return blogSearchKeywordSpringDataRepository.findAll(request)
    }

    /**
     * processed dtm 이 null 이 아닌 모든 데이터 삭제.
     * (배치 등을 통해서 목적이 상실된 데이터를 infra 에서 삭제하기 위함)
     */
    @CacheEvict(value = [POPULAR_KEYWORD])
    fun deleteAllByProcessedDtmIsNotNull() {
        blogSearchKeywordSpringDataRepository.deleteByProcessedDtmIsNotNull()
    }
}
