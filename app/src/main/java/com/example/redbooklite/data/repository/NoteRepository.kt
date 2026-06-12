package com.example.redbooklite.data.repository

import android.content.Context
import com.example.redbooklite.R
import com.example.redbooklite.data.local.NoteLocalStore
import com.example.redbooklite.data.local.SeedData
import com.example.redbooklite.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepository(context: Context) {

    private val localStore = NoteLocalStore()
    private val appContext = context.applicationContext

    suspend fun initDataIfNeeded() {
        if (localStore.isEmpty()) {
            localStore.saveAll(SeedData.buildNotes(appContext))
        }
    }

    fun getFeedNotes(): Flow<List<Note>> {
        return localStore.notesFlow.map { list ->
            list.sortedByDescending { it.createdAt }
        }
    }

    fun getNoteById(noteId: Long): Note? {
        return localStore.getById(noteId)
    }

    suspend fun likeNote(noteId: Long) {
        val note = localStore.getById(noteId) ?: return
        localStore.update(note.copy(likeCount = note.likeCount + 1))
    }

    fun publishNote(note: Note) {
        localStore.add(note)
    }

    fun createNextNoteId(): Long {
        return localStore.generateNextId()
    }

    fun getAuthorMe(): String {
        return appContext.getString(R.string.author_me)
    }
}
