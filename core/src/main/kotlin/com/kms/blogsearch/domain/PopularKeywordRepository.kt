package com.kms.blogsearch.domain

/**
 * 인기 검색어 repository
 */
interface PopularKeywordRepository {

    /**
     * 조회수에 따라 정렬된 검색어 상위 10개 조회
     */
    fun findTop10ByOrderByCountDesc(): List<PopularKeyword>
}
