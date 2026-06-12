package com.example.redbooklite.data.local

import com.example.redbooklite.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteLocalStore {

    private val notesState = MutableStateFlow<List<Note>>(emptyList())
    val notesFlow: StateFlow<List<Note>> = notesState.asStateFlow()
    private var nextId = 1L

    fun isEmpty(): Boolean = notesState.value.isEmpty()

    fun generateNextId(): Long {
        val id = nextId
        nextId++
        return id
    }

    fun saveAll(notes: List<Note>) {
        notesState.value = notes
        nextId = (notes.maxOfOrNull { it.id } ?: 0L) + 1L
    }

    fun add(note: Note) {
        saveAll(notesState.value + note)
    }

    fun update(note: Note) {
        saveAll(notesState.value.map { if (it.id == note.id) note else it })
    }

    fun getById(noteId: Long): Note? = notesState.value.find { it.id == noteId }
}
