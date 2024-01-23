package com.kms.blogsearch.aop

import com.kms.blogsearch.domain.BlogSearchKeywordExternalRepository
import com.kms.blogsearch.dto.BlogSearchRequestDto
import com.kms.blogsearch.dto.SortType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationEventPublisher

/*
class BlogSearchExternalAdapter(
    private val kakaoApiOpenFeign: KakaoApiOpenFeign,
    private val naverApiOpenFeign: NaverApiOpenFeign
): BlogSearchKeywordExternalRepository
 */
@SpringBootTest(classes = [BlogSearchByKeywordAspect::class])
class BlogSearchByKeywordAspectTest{

    val blogSearchKeywordExternalRepository: BlogSearchKeywordExternalRepository = mockk()

    val eventPublisher: ApplicationEventPublisher = mockk()

    private lateinit var blogSearchByKeywordAspect: BlogSearchByKeywordAspect

    @BeforeEach
    fun setup() {
        blogSearchByKeywordAspect = BlogSearchByKeywordAspect(eventPublisher)
    }

    @Test
    fun weavingTest() {
        every { blogSearchKeywordExternalRepository.searchBlog(
            BlogSearchRequestDto("test", SortType.ACCURACY, 1, 1)
        ) } returns mockk()


        verify(exactly = 1) {
            eventPublisher.publishEvent(any())
        }
    }

}