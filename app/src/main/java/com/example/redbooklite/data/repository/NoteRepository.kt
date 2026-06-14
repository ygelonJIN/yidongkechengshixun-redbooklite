package com.example.redbooklite.data.repository

import android.content.Context
import com.example.redbooklite.R
import com.example.redbooklite.data.local.LikeStore
import com.example.redbooklite.data.local.NoteLocalStore
import com.example.redbooklite.data.local.SeedData
import com.example.redbooklite.data.remote.NoteDto
import com.example.redbooklite.data.remote.RetrofitClient
import com.example.redbooklite.model.Note
import com.example.redbooklite.model.NoteCategory
import kotlinx.coroutines.flow.StateFlow

class NoteRepository(context: Context) {

    private val localStore = NoteLocalStore()
    private val likeStore = LikeStore(context.applicationContext)
    private val appContext = context.applicationContext
    private val likedIds = mutableSetOf<Long>()

    suspend fun initDataIfNeeded() {
        if (localStore.isEmpty()) {
            val seeded = SeedData.buildNotes(appContext)
            localStore.saveAll(seeded.map { it.copy(isLiked = likeStore.isLiked(it.id) || likedIds.contains(it.id)) })
        }
    }

    suspend fun refreshFeed(category: NoteCategory = NoteCategory.ALL): Result<List<Note>> {
        return runCatching {
            val remoteDtos: List<NoteDto> = RetrofitClient.feedApiService.getNotes(
                category = if (category == NoteCategory.ALL) null else category.apiValue
            )
            val remote = remoteDtos.map { it.toDomain().withLikeState(likeStore.isLiked(it.id)) }
            localStore.saveAll(remote)
            remote
        }.recoverCatching {
            localStore.getByCategory(category).sortedByDescending { it.createdAt }
        }
    }

    fun getFeedNotes(category: NoteCategory = NoteCategory.ALL): StateFlow<List<Note>> {
        return localStore.notesFlow
    }

    fun getNoteById(noteId: Long): Note? {
        return localStore.getById(noteId)
    }

    suspend fun toggleLike(noteId: Long) {
        val note = localStore.getById(noteId) ?: return
        val liked = likeStore.isLiked(noteId) || likedIds.contains(noteId)
        if (liked) return
        likeStore.like(noteId)
        likedIds.add(noteId)
        localStore.update(note.copy(likeCount = note.likeCount + 1, isLiked = true))
    }

    suspend fun likeNote(noteId: Long) = toggleLike(noteId)

    fun publishNote(note: Note) {
        localStore.add(note.copy(isLiked = likeStore.isLiked(note.id) || likedIds.contains(note.id)))
    }

    fun createNextNoteId(): Long {
        return localStore.generateNextId()
    }

    fun getAuthorMe(): String {
        return appContext.getString(R.string.author_me)
    }

    private fun Note.withLikeState(isLiked: Boolean): Note {
        return copy(isLiked = isLiked)
    }
}
