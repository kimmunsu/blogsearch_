package infrastructure

import com.kms.blogsearch.domain.BlogSearchKeyword
import com.kms.blogsearch.domain.BlogSearchKeywordRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class BlogSearchKeywordPersistenceAdapter(
    private val blogSearchKeywordSpringDataRepository: BlogSearchKeywordSpringDataRepository
) : BlogSearchKeywordRepository {

    override fun save(blogSearchKeyword: BlogSearchKeyword) {
        blogSearchKeywordSpringDataRepository.save(blogSearchKeyword)
    }

    override fun findAllWithPaging(request: PageRequest): Page<BlogSearchKeyword> {
        return blogSearchKeywordSpringDataRepository.findAll(request)
    }
}
