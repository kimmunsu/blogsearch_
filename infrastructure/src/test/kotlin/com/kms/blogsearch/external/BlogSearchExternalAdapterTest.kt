package com.kms.blogsearch.external

import com.kms.blogsearch.dto.BlogPageDto
import com.kms.blogsearch.dto.BlogSearchRequestDto
import com.kms.blogsearch.dto.SortType
import com.kms.blogsearch.external.dto.KakaoBlogSearchResponse
import com.kms.blogsearch.external.dto.NaverBlogSearchResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class BlogSearchExternalAdapterTest {

    private val kakaoApiOpenFeign: KakaoApiOpenFeign = mockk()
    private val naverApiOpenFeign: NaverApiOpenFeign = mockk()

    private lateinit var blogSearchExternalAdapter: BlogSearchExternalAdapter

    @BeforeEach
    fun setup() {
        this.blogSearchExternalAdapter = BlogSearchExternalAdapter(
            kakaoApiOpenFeign,
            naverApiOpenFeign
        )
    }

    @Test
    fun `searchBlog no exception`() {
        every { kakaoApiOpenFeign.search(any()) } returns mockk<KakaoBlogSearchResponse>{
            every { totalCount } returns 0
            every { toDto() } returns BlogPageDto(
                0, 0, true, emptyList()
            )
        }

        val result = blogSearchExternalAdapter.searchBlog(BlogSearchRequestDto(
            "test", SortType.ACCURACY, 1, 1
        ))
        assertEquals(result.totalCount, 0)
    }

    @Test
    fun `searchBlog occur exception kakao api, execute naver api`() {
        every { kakaoApiOpenFeign.search(any()) } throws Exception("ERROR")
        every { naverApiOpenFeign.search(any()) } returns mockk<NaverBlogSearchResponse> {
            every { total } returns 0
            every { toDto() } returns BlogPageDto(
                0, 0, true, emptyList()
            )
        }
        val result = blogSearchExternalAdapter.searchBlog(BlogSearchRequestDto(
            "test", SortType.ACCURACY, 1, 1
        ))

        assertEquals(result.totalCount, 0)
        verify(exactly = 1) { naverApiOpenFeign.search(any()) }
    }

}