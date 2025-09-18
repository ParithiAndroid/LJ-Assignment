package com.parithidb.ljassignment.ui.screens.theme

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel to manage dark/light theme toggle.
 * Uses StateFlow to expose theme state to Composables.
 */

@HiltViewModel
class ThemeViewModel @Inject constructor() : ViewModel() {

    // Holds current theme state (true = dark, false = light)
    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    /**
     * Toggle between dark and light theme.
     */

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
}
