package com.kms.blogsearch

import io.mockk.mockk
import jakarta.persistence.EntityManagerFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PopularKeywordJobConfigurationTest {

    private val blogSearchKeywordRepository: BlogSearchKeywordPersistenceAdapter = mockk()
    private val keywordCountManager: KeywordCountManager = mockk()
    private val entityManagerFactory: EntityManagerFactory = mockk()

    private lateinit var popularKeywordJobConfiguration: PopularKeywordJobConfiguration

    @BeforeEach
    fun setup() {
        popularKeywordJobConfiguration = PopularKeywordJobConfiguration(
            blogSearchKeywordRepository, keywordCountManager, entityManagerFactory
        )
    }

}