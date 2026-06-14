package com.example.redbooklite.data.remote

import com.example.redbooklite.model.Note
import com.example.redbooklite.model.NoteCategory

fun NoteDto.toDomain(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        coverPath = cover_path,
        coverAspectRatio = 1.0f,
        authorName = author.name,
        likeCount = like_count,
        isMine = false,
        createdAt = created_at,
        category = NoteCategory.fromKey(category)
    )
}

fun Note.toDto(): NoteDto {
    return NoteDto(
        id = id,
        title = title,
        content = content,
        cover_path = coverPath,
        author = AuthorDto(authorName, null),
        like_count = likeCount,
        created_at = createdAt,
        category = category.key
    )
}

val NoteCategory.apiValue: String
    get() = key
