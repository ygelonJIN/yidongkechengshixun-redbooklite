package com.example.redbooklite.model

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val coverPath: String,
    /** 封面宽高比 width / height，用于瀑布流计算封面高度 */
    val coverAspectRatio: Float,
    val authorName: String,
    val avatarPath: String = coverPath,
    val likeCount: Int,
    val isLiked: Boolean = false,
    val isMine: Boolean,
    val createdAt: Long,
    val category: NoteCategory = NoteCategory.ALL
)
