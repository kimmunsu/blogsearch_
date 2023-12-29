package infrastructure.external.dto

import com.kms.blogsearch.dto.BlogSearchRequestDto

data class NaverBlogSearchRequest(
    val query: String,
    val display: Int,   // size
    val start: Int,     // page
    val sort: String
) {
    constructor(request : BlogSearchRequestDto): this(
        query = request.keyword,
        display = request.size,
        start = request.page,
        sort = request.sorting.naver
    )
}
