package com.kms.blogsearch.domain

interface PopularKeywordRepository {
    fun findTop10PopularKeyword(): List<PopularKeyword>
}