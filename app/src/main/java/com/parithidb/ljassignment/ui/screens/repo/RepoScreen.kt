package com.parithidb.ljassignment.ui.screens.repo

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.parithidb.ljassignment.ui.screens.web.WebActivity
import kotlin.jvm.java

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoScreen(
    viewModel: RepoViewModel = hiltViewModel(),
    onToggleTheme: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trending Swift Repos") },
                actions = {
                    IconButton(onClick = { onToggleTheme() }) {
                        Icon(
                            imageVector = Icons.Default.Brightness6, // 🌙/☀️ icon
                            contentDescription = "Toggle Dark/Light Mode"
                        )
                    }
                    IconButton(onClick = { viewModel.refreshRepos() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is RepoViewModel.UiState.Loading -> {
                    CircularProgressIndicator()
                }

                is RepoViewModel.UiState.Success -> {
                    val repos = (state as RepoViewModel.UiState.Success).repos
                    LazyColumn {
                        items(repos) { repo ->
                            val context = LocalContext.current
                            RepoItem(repo) { url, repoName ->
                                context.startActivity(
                                    Intent(context, WebActivity::class.java).apply {
                                        putExtra(WebActivity.EXTRA_URL, url)
                                        putExtra(WebActivity.REPO_NAME, repoName)
                                    }
                                )
                            }
                        }
                    }

                }

                is RepoViewModel.UiState.Error -> {
                    val message = (state as RepoViewModel.UiState.Error).message
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: $message", color = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { viewModel.refreshRepos() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}
