package com.example.redbooklite.data.remote

import com.example.redbooklite.model.Note
import com.example.redbooklite.model.NoteCategory

data class ApiResponse<T>(
    val code: Int = 0,
    val message: String = "",
    val data: T? = null
)

data class FeedResponse(
    val items: List<NoteDto> = emptyList()
)

data class NoteDto(
    val id: Long,
    val title: String,
    val content: String,
    val cover_path: String,
    val author: AuthorDto,
    val like_count: Int,
    val created_at: Long,
    val category: String = "all"
) {
    fun toDomain(): Note = Note(
        id = id,
        title = title,
        content = content,
        coverPath = cover_path,
        coverAspectRatio = 1f,
        authorName = author.name,
        avatarPath = author.avatar_url ?: cover_path,
        likeCount = like_count,
        isLiked = false,
        isMine = false,
        createdAt = created_at,
        category = NoteCategory.fromApiValue(category)
    )
}

data class AuthorDto(
    val name: String,
    val avatar_url: String? = null
)
