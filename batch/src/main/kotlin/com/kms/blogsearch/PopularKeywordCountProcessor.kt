package com.kms.blogsearch

import com.kms.blogsearch.domain.BlogSearchKeyword
import com.kms.blogsearch.domain.PopularKeyword
import org.springframework.batch.item.ItemProcessor
import org.springframework.context.annotation.Bean
import java.time.LocalDateTime

class PopularKeywordCountProcessor(
    private val blogSearchKeywordRepository: BlogSearchKeywordSpringDataRepository,
    private val popularKeywordRepository: PopularKeywordSpringDataRepository
): ItemProcessor<List<BlogSearchKeyword>, List<PopularKeyword>> {

    override fun process(item: List<BlogSearchKeyword>): List<PopularKeyword> {
        val processedDtm = LocalDateTime.now()

        val count = mutableMapOf<String, PopularKeyword>()
        item.forEach {
            if (count[it.keyword] != null) {
                count[it.keyword]!!.count++
            } else {
                var exist = popularKeywordRepository.findByKeyword(it.keyword)
                if (exist == null) {
                   exist = PopularKeyword().apply {
                       this.keyword = it.keyword
                       this.count = 0
                       this.createdDtm = processedDtm
                   }
                }
                exist.count++
                exist.modifiedDtm = processedDtm
                count[it.keyword] = exist

                it.processedDtm = processedDtm
                blogSearchKeywordRepository.save(it)
            }
        }

        return count.values.toList()

    }
}
/*
class ListItemProcessor : ItemProcessor<List<String>, List<String>> {
    override fun process(items: List<String>): List<String> {
        // 각 아이템에 대한 처리 로직을 수행하고, 처리된 결과를 반환
        return items.map { it.toUpperCase() }
    }
}
 */