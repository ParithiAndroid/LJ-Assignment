package com.parithidb.ljassignment.ui.screens.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parithidb.ljassignment.data.database.entities.RepoEntity
import com.parithidb.ljassignment.data.repository.ReposRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for repository list screen.
 * Handles fetching, caching, search, and error events.
 */

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repository: ReposRepository
) : ViewModel() {

    // Sealed class representing UI state
    sealed class UiState {
        object Loading : UiState()
        data class Success(val repos: List<RepoEntity>) : UiState()
        data class Error(val message: String) : UiState()
        object Empty : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // API error messages to show as snackbar
    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents: SharedFlow<String> = _errorEvents.asSharedFlow()

    init {
        // Observe DB continuously
        viewModelScope.launch {
            repository.getRepos().collect { repos ->
                _uiState.value = when {
                    repos.isNotEmpty() -> UiState.Success(repos)
                    else -> UiState.Loading
                }
            }
        }

        // Initial API fetch
        refreshRepos()
    }

    /**
     * Refresh repository list from API.
     * Shows snackbar on failure, retains local data if available.
     */

    fun refreshRepos() {
        viewModelScope.launch {
            val localRepos = repository.getRepos().first() // get snapshot

            val result = repository.refreshRepos()

            result.onFailure { e ->
                if (localRepos.isNotEmpty()) {
                    // Show snackbar, keep old list
                    _errorEvents.emit(e.message ?: "Unknown error")
                    _uiState.value = UiState.Success(localRepos)
                } else {
                    // No local data, show full-screen error
                    _uiState.value = UiState.Error(e.message ?: "Unknown error")
                }
            }
        }
    }

    /**
     * Update the search query for filtering the list.
     */

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * Filter repositories based on search query.
     */

    fun filteredRepos(allRepos: List<RepoEntity>): List<RepoEntity> {
        val query = _searchQuery.value.lowercase()
        return if (query.isBlank()) {
            allRepos
        } else {
            allRepos.filter { repo ->
                repo.repoName.lowercase().contains(query) ||
                        repo.ownerName.lowercase().contains(query) ||
                        repo.id.toString().contains(query)
            }
        }
    }
}
