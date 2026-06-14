package com.example.redbooklite.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.redbooklite.data.repository.NoteRepository
import com.example.redbooklite.model.Note
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: NoteRepository,
    private val noteId: Long
) : ViewModel() {

    private val _note = MutableLiveData<Note?>()
    val note: LiveData<Note?> = _note

    private var observeJob: Job? = null

    fun loadNote() {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            repository.getFeedNotes().collect { notes ->
                _note.value = notes.firstOrNull { it.id == noteId }
            }
        }
    }

    fun likeNote() {
        viewModelScope.launch {
            repository.toggleLike(noteId)
        }
    }
}

class DetailViewModelFactory(
    private val repository: NoteRepository,
    private val noteId: Long
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository, noteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
