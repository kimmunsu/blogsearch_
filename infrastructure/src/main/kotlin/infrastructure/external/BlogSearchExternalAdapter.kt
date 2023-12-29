package infrastructure.external

import com.kms.blogsearch.domain.BlogSearchKeywordExternalRepository
import com.kms.blogsearch.dto.BlogPageDto
import com.kms.blogsearch.dto.BlogSearchRequestDto
import infrastructure.external.dto.KakaoBlogSearchRequest
import infrastructure.external.dto.NaverBlogSearchRequest
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Repository

@Repository
class BlogSearchExternalAdapter(
    private val kakaoApiOpenFeign: KakaoApiOpenFeign,
    private val naverApiOpenFeign: NaverApiOpenFeign
): BlogSearchKeywordExternalRepository {

    @PostConstruct
    fun init() {
        println("############################################## ")
    }

    override fun searchBlog(request: BlogSearchRequestDto): BlogPageDto {
        return try {
                kakaoApiOpenFeign.search(KakaoBlogSearchRequest(request)).toDto()
            } catch (e: Exception) {
                naverApiOpenFeign.search(NaverBlogSearchRequest(request)).toDto()
            }
    }
}
