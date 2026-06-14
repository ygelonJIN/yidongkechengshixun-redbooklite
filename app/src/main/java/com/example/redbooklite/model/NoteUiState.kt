package com.example.redbooklite.model

data class NoteUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val category: NoteCategory = NoteCategory.ALL
)
