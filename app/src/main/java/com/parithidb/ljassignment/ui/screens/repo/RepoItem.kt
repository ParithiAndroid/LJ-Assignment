package com.parithidb.ljassignment.ui.screens.repo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parithidb.ljassignment.data.database.entities.RepoEntity
import com.parithidb.ljassignment.util.LoadImage

@Composable
fun RepoItem(repo: RepoEntity, onClick: (String, String) -> Unit) {
    val cardHeight = 120.dp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(8.dp)
            .clickable { onClick(repo.repoUrl, repo.repoName) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Image touches top, bottom, start
            LoadImage(
                imageUrl = repo.imageUrl,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.width(16.dp)) // space between image and text

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "ID: ${repo.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = repo.repoName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Owner: ${repo.ownerName}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
