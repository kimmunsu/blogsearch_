package com.kms.blogsearch

import com.kms.blogsearch.domain.PopularKeyword
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.EntityManagerFactory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PopularKeywordJobConfigurationTest {

    private val blogSearchKeywordRepository: BlogSearchKeywordPersistenceAdapter = mockk()
    private val popularKeywordRepository: PopularKeywordPersistenceAdapter = mockk()
    private val entityManagerFactory: EntityManagerFactory = mockk()

    private lateinit var popularKeywordJobConfiguration: PopularKeywordJobConfiguration

    @BeforeEach
    fun setup() {
        popularKeywordJobConfiguration = PopularKeywordJobConfiguration(
            blogSearchKeywordRepository, popularKeywordRepository, entityManagerFactory
        )
    }

    @Test
    fun `keywordCount 이미 step 실행중에 keyword 가 있어서 keywordcountmap 에 데이터가 있는 경우`() {
        popularKeywordJobConfiguration.keywordCountMap =
            mutableMapOf(
                "test" to PopularKeyword().apply {
                    keyword = "test"
                    count = 5
                }
            )
        val result = popularKeywordJobConfiguration.keywordCount("test")
        assertEquals(result.count, 6)
        verify(exactly = 0) { popularKeywordRepository.findByKeyword(any()) }
    }

    @Test
    fun `keywordCount popularKeywordRepository 조회로 데이터가 있는 경우`() {
        popularKeywordJobConfiguration.keywordCountMap = mutableMapOf()
        every { popularKeywordRepository.findByKeyword(any()) } returns PopularKeyword().apply {
            this.keyword = "test"
            this.count = 5
        }

        val result = popularKeywordJobConfiguration.keywordCount("test")
        assertEquals(result.count, 6)
    }

    @Test
    fun `keywordCount 처음 조회된, 카운트 되지 않은 키워드인 경우`() {
        popularKeywordJobConfiguration.keywordCountMap = mutableMapOf()
        every { popularKeywordRepository.findByKeyword(any()) } returns null
        val result = popularKeywordJobConfiguration.keywordCount("test")

        assertEquals(result.count, 1)
    }

}