package com.parithidb.ljassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.parithidb.ljassignment.ui.screens.repo.RepoScreen
import com.parithidb.ljassignment.ui.screens.theme.ThemeViewModel
import com.parithidb.ljassignment.ui.theme.LJAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            LJAssignmentTheme(darkTheme = isDarkTheme) {
                RepoScreen(
                    onToggleTheme = { themeViewModel.toggleTheme() }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightPreview() {
    LJAssignmentTheme(darkTheme = false) {
        RepoScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkPreview() {
    LJAssignmentTheme(darkTheme = true) {
        RepoScreen()
    }
}
