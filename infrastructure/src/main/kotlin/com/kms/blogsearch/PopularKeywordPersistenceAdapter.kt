package com.kms.blogsearch

import com.kms.blogsearch.domain.PopularKeyword
import com.kms.blogsearch.domain.PopularKeywordRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository

/**
 * 인기 검색어 repository 구현 클래스
 */
@Repository
class PopularKeywordPersistenceAdapter(
    private val popularKeywordSpringDataRepository: PopularKeywordSpringDataRepository
) : PopularKeywordRepository {

    /**
     * 인기검색어 조회 많은 순 정렬 상위 10개까지 조회 - cache
     */
    @Cacheable(value = [POPULAR_KEYWORD])
    override fun findTop10ByOrderByCountDesc(): List<PopularKeyword> {
        return popularKeywordSpringDataRepository.findTop10ByOrderByCountDesc()
    }

    /**
     * 검색어로 nullable 조회
     */
    fun findByKeyword(keyword: String): PopularKeyword? {
        return popularKeywordSpringDataRepository.findByKeyword(keyword)
    }
}
