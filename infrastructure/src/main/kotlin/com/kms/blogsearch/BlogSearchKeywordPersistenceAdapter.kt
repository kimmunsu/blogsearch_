package com.kms.blogsearch

import com.kms.blogsearch.domain.BlogSearchKeyword
import com.kms.blogsearch.domain.BlogSearchKeywordRepository
import com.kms.blogsearch.domain.PopularKeyword
import jakarta.annotation.PostConstruct
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Random

@Repository
class BlogSearchKeywordPersistenceAdapter(
    private val blogSearchKeywordSpringDataRepository: BlogSearchKeywordSpringDataRepository
) : BlogSearchKeywordRepository {

    @PostConstruct
    fun saveTemp() {
        var test = listOf("test", "가나다라", "1234asdg", "ㅓㅂㅈㄷ사ㅣㅓㅣㅓ")
        for (i in 0..100) {
            BlogSearchKeyword().apply {
                this.keyword = test[Random().nextInt(4)]
                this.createdDtm = LocalDateTime.now()
            }.let {
                save(it)
            }
        }
    }
    override fun save(blogSearchKeyword: BlogSearchKeyword) {
        blogSearchKeywordSpringDataRepository.save(blogSearchKeyword)
    }

    override fun findAllWithPaging(request: PageRequest): Page<BlogSearchKeyword> {
        return blogSearchKeywordSpringDataRepository.findAll(request)
    }
}
