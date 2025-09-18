package com.parithidb.ljassignment.data.api.model

import com.google.gson.annotations.SerializedName
import com.parithidb.ljassignment.data.database.entities.RepoEntity

/**
 * Data class representing a GitHub repository returned from API.
 *
 * @property id Unique repository ID
 * @property name Full name of the repository in format "owner/repo"
 * @property repoURL HTML URL of the repository
 */

data class GHRepo(
    @SerializedName("id") val id: Long,
    @SerializedName("full_name") val name: String,
    @SerializedName("html_url") val repoURL: String,
)

/**
 * Extension function to convert [GHRepo] API model to [RepoEntity] database entity.
 * Splits the full name to extract owner and repository name.
 */

fun GHRepo.toEntity(): RepoEntity {
    val parts = name.split("/")
    val owner = parts.getOrNull(0) ?: ""
    val repo = parts.getOrNull(1) ?: name
    return RepoEntity(
        id = id,
        repoName = repo,
        ownerName = owner,
        repoUrl = repoURL,
        // Construct avatar URL using owner's GitHub username
        imageUrl = "https://avatars.githubusercontent.com/${owner}"
    )
}
