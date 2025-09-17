package com.parithidb.ljassignment.ui.screens.web

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.parithidb.ljassignment.ui.theme.LJAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebActivity : ComponentActivity() {

    companion object {
        const val EXTRA_URL = "extra_url"
        const val REPO_NAME = "repo_name"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val url = intent.getStringExtra(EXTRA_URL) ?: ""
            val repoName = intent.getStringExtra(REPO_NAME) ?: ""
            LJAssignmentTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(repoName) })
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        AndroidView(
                            factory = { context ->
                                WebView(context).apply {
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                    webViewClient = WebViewClient()
                                    settings.javaScriptEnabled = true
                                    loadUrl(url)
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
