package com.kms.blogsearch.domain

import com.kms.blogsearch.dto.BlogSearchKeywordDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * 블로그 검색 keyword service
 */
@Service
class BlogSearchKeywordService(
    val blogSearchKeywordRepository: BlogSearchKeywordRepository
) {

    /**
     * 블로그 검색 keyword 저장
     */
    @Transactional
    fun save(keyword: String) {
        blogSearchKeywordRepository.save(
            BlogSearchKeyword().apply {
                this.keyword = keyword
                this.createdDtm = LocalDateTime.now()
            }
        )
    }

    /**
     * 블로그 검색 keyword 전체 조회
     */
    @Transactional(readOnly = true)
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