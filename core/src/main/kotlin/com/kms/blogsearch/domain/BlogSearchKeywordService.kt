package com.kms.blogsearch.domain

import com.kms.blogsearch.dto.BlogSearchKeywordDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BlogSearchKeywordService(
    val blogSearchKeywordRepository: BlogSearchKeywordRepository
) {
    fun save(keyword: String) {
        blogSearchKeywordRepository.save(
            BlogSearchKeyword().apply {
                this.keyword = keyword
                this.createdDtm = LocalDateTime.now()
            }
        )
    }

    fun findAllWithPaging(request: PageRequest): Page<BlogSearchKeywordDto> {
        val result: Page<BlogSearchKeyword> = blogSearchKeywordRepository.findAllWithPaging(request)
        return result.let { keyword ->
            PageImpl(
                keyword.content.map { BlogSearchKeywordDto.of(it) },
                keyword.pageable,
                keyword.totalElements
            )
        }
    }
}