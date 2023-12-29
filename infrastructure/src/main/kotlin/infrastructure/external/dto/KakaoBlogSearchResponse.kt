package infrastructure.external.dto

import com.kms.blogsearch.dto.BlogDto
import com.kms.blogsearch.dto.BlogPageDto

data class KakaoBlogSearchResponse(
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean,
    val documents: List<KakaoBlogDocument>
) {
    fun toDto(): BlogPageDto {
        return BlogPageDto(
            totalCount,
            pageableCount,
            isEnd,
            documents.map{ it.toDto() }
        )
    }
}

data class KakaoBlogDocument(
    val blogname: String,
    val title: String,
    val contents: String,
    val url: String,
    val datetime: String,
    val thumbnail: String
){
    fun toDto(): BlogDto {
        return BlogDto(
            blogname,
            title,
            contents,
            url,
            datetime,
            thumbnail
        )

    }
}
