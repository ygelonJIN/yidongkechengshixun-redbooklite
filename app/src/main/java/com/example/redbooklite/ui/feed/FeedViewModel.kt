package com.example.redbooklite.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.redbooklite.data.repository.NoteRepository
import com.example.redbooklite.model.Note

class FeedViewModel(repository: NoteRepository) : ViewModel() {
    val notes: LiveData<List<Note>> = repository.getFeedNotes().asLiveData()
}

class FeedViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            return FeedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
