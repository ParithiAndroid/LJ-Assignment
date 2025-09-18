package com.parithidb.ljassignment.ui.screens.repo

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.parithidb.ljassignment.ui.screens.web.WebActivity
import kotlinx.coroutines.launch
import kotlin.jvm.java

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoScreen(
    viewModel: RepoViewModel = hiltViewModel(),
    onToggleTheme: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var searchMode by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
            }
        }
    }



// Watch scroll state to clear focus
    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            focusManager.clearFocus()
        }
    }

    LaunchedEffect(searchMode) {
        if (searchMode) {
            focusRequester.requestFocus()
            keyboardController?.show()
        } else {
            keyboardController?.hide()
        }
    }


    BackHandler(enabled = searchMode) {
        searchMode = false
        viewModel.updateSearchQuery("")
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    AnimatedContent(
                        targetState = searchMode,
                        transitionSpec = {
                            if (targetState) {
                                slideInHorizontally(
                                    initialOffsetX = { fullWidth -> fullWidth },
                                    animationSpec = tween(300)
                                ) + fadeIn() togetherWith
                                        slideOutHorizontally(
                                            targetOffsetX = { fullWidth -> -fullWidth / 2 },
                                            animationSpec = tween(300)
                                        ) + fadeOut()
                            } else {
                                slideInHorizontally(
                                    initialOffsetX = { fullWidth -> -fullWidth },
                                    animationSpec = tween(300)
                                ) + fadeIn() togetherWith
                                        slideOutHorizontally(
                                            targetOffsetX = { fullWidth -> fullWidth },
                                            animationSpec = tween(300)
                                        ) + fadeOut()
                            }
                        },
                        label = "TopBarSlideAnim"
                    ) { isSearching ->
                        if (isSearching) {
                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                TextField(
                                    value = searchQuery,
                                    onValueChange = { viewModel.updateSearchQuery(it) },
                                    placeholder = { Text("Search repos...", fontSize = 14.sp) },
                                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp) // keeps it centered
                                        .focusRequester(focusRequester),
                                    shape = RoundedCornerShape(16.dp),
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Search",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    },
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        errorContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent,
                                        errorIndicatorColor = Color.Transparent,
                                        cursorColor = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        } else {
                            Text("Swift Repos")
                        }
                    }
                },
                actions = {
                    Row {
                        //  Animate only search/close toggle
                        AnimatedContent(
                            targetState = searchMode,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(200)) togetherWith
                                        fadeOut(animationSpec = tween(200))
                            },
                            label = "SearchToggleAnim"
                        ) { isSearching ->
                            if (isSearching) {
                                IconButton(onClick = {
                                    viewModel.updateSearchQuery("")
                                    searchMode = false
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close Search"
                                    )
                                }
                            } else {
                                IconButton(onClick = { searchMode = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search"
                                    )
                                }
                            }
                        }

                        // Always visible theme toggle
                        IconButton(onClick = { onToggleTheme() }) {
                            Icon(
                                imageVector = Icons.Default.Brightness6,
                                contentDescription = "Toggle Dark/Light Mode"
                            )
                        }

                        // Always visible refresh
                        IconButton(onClick = { viewModel.refreshRepos() }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
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
                    // keep centered only for loader
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is RepoViewModel.UiState.Success -> {
                    val repos = (state as RepoViewModel.UiState.Success).repos
                    val searchQuery by viewModel.searchQuery.collectAsState()
                    val filteredRepos = remember(repos, searchQuery) {
                        viewModel.filteredRepos(repos)
                    }

                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        items(filteredRepos) { repo ->
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

                is RepoViewModel.UiState.Empty -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No repos available")
                    }
                }

                is RepoViewModel.UiState.Error -> {
                    val message = (state as RepoViewModel.UiState.Error).message
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
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
