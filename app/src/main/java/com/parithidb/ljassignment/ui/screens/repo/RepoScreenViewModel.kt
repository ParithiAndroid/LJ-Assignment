package com.parithidb.ljassignment.ui.screens.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parithidb.ljassignment.data.database.entities.RepoEntity
import com.parithidb.ljassignment.data.repository.ReposRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repository: ReposRepository
) : ViewModel() {

    // UI state sealed class
    sealed class UiState {
        object Loading : UiState()
        data class Success(val repos: List<RepoEntity>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        // Start observing DB
        viewModelScope.launch {
            repository.getRepos().collect { repos ->
                if (repos.isNotEmpty()) {
                    _uiState.value = UiState.Success(repos)
                } else {
                    _uiState.value = UiState.Loading
                }
            }
        }
        // Kick off initial refresh
        refreshRepos()
    }

    fun refreshRepos() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repository.refreshRepos()
            result.onFailure { e ->
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
