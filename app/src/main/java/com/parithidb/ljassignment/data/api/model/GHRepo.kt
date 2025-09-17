package com.parithidb.ljassignment.data.api.model

import com.google.gson.annotations.SerializedName
import com.parithidb.ljassignment.data.database.entities.RepoEntity

data class GHRepo(
    @SerializedName("id") val id: Long,
    @SerializedName("full_name") val name: String,
    @SerializedName("html_url") val repoURL: String,
)

fun GHRepo.toEntity(): RepoEntity {
    val parts = name.split("/")
    val owner = parts.getOrNull(0) ?: ""
    val repo = parts.getOrNull(1) ?: name
    return RepoEntity(
        id = id,
        repoName = repo,
        ownerName = owner,
        repoUrl = repoURL,
        imageUrl = "https://avatars.githubusercontent.com/${owner}"
    )
}
