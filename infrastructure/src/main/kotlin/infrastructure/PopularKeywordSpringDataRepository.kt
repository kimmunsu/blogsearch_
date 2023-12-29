package infrastructure

import com.kms.blogsearch.domain.PopularKeyword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PopularKeywordSpringDataRepository : JpaRepository<PopularKeyword, Long> {
    fun findTop10ByOrderByCountDesc(): List<PopularKeyword>
    fun findByKeyword(keyword: String): PopularKeyword?
}
