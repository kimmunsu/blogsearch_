package com.kms.blogsearch

import com.kms.blogsearch.domain.PopularKeyword
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class KeywordCountManager(
    private val popularKeywordRepository: PopularKeywordPersistenceAdapter,
    private val blogSearchKeywordRepository: BlogSearchKeywordPersistenceAdapter
) {
    // list reader, list processor, list writer 에 대한 구현중 진행이 안되어 인메모리 사용.
    val keywordCountMap: MutableMap<String, PopularKeyword> = mutableMapOf()

    @Transactional
    fun clear() {
        keywordCountMap.clear()
        blogSearchKeywordRepository.deleteAllByProcessedDtmIsNotNull()
    }

    @Transactional
    fun keywordCount(keyword: String) : PopularKeyword{
        var popularKeyword = keywordCountMap[keyword]

        if (popularKeyword != null) {
            keywordCountMap[keyword]!!.count++
        } else {
            // find by keyword
            popularKeyword = popularKeywordRepository.findByKeyword(keyword)
            if (popularKeyword == null) {
                popularKeyword = PopularKeyword().apply {
                    this.keyword = keyword
                    this.count = 0
                    this.createdDtm = LocalDateTime.now()
                }
            }
            // update
            popularKeyword.apply {
                this.count++
                this.modifiedDtm = LocalDateTime.now()
            }
            keywordCountMap[keyword] = popularKeyword
        }

        return popularKeyword
    }
}