package com.example.redbooklite.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.redbooklite.data.repository.NoteRepository
import com.example.redbooklite.model.Note
import com.example.redbooklite.model.NoteCategory
import com.example.redbooklite.model.NoteUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FeedViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _uiState = MutableLiveData(NoteUiState(isLoading = true))
    val uiState: LiveData<NoteUiState> = _uiState

    private var currentCategory: NoteCategory = NoteCategory.ALL
    private var currentQuery: String = ""
    private var feedCollectJob: Job? = null

    init {
        observeFeed()
        loadFeed()
    }

    fun loadFeed(category: NoteCategory = currentCategory, forceRefresh: Boolean = false) {
        currentCategory = category
        _uiState.value = _uiState.value?.copy(
            isLoading = !forceRefresh,
            isRefreshing = forceRefresh,
            category = category,
            errorMessage = null
        )
        viewModelScope.launch {
            repository.refreshFeed(category)
                .onSuccess {
                    _uiState.value = _uiState.value?.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = null,
                        category = category
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value?.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = error.message ?: "加载失败",
                        category = category
                    )
                }
        }
    }

    fun selectCategory(category: NoteCategory) {
        if (currentCategory == category) {
            refreshVisibleNotes()
            return
        }
        loadFeed(category, forceRefresh = true)
    }

    fun refreshVisibleNotes() {
        updateNotes(repository.getFeedNotes(currentCategory).value)
    }

    fun refreshCurrentFeed() {
        loadFeed(currentCategory, forceRefresh = true)
    }

    fun search(keyword: String) {
        currentQuery = keyword.trim()
        refreshVisibleNotes()
    }

    private fun observeFeed() {
        feedCollectJob?.cancel()
        feedCollectJob = viewModelScope.launch {
            repository.getFeedNotes().collect { notes ->
                updateNotes(notes)
            }
        }
    }

    private fun updateNotes(source: List<Note>) {
        _uiState.value = _uiState.value?.copy(notes = filterNotes(source))
    }

    private fun filterNotes(source: List<Note>): List<Note> {
        return source.asSequence()
            .filter { currentCategory == NoteCategory.ALL || it.category == currentCategory }
            .filter {
                currentQuery.isBlank() || it.title.contains(currentQuery, ignoreCase = true) || it.content.contains(currentQuery, ignoreCase = true)
            }
            .sortedByDescending { it.createdAt }
            .toList()
    }
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
