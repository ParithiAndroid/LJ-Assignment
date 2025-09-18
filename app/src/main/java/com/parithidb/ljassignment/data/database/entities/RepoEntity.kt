package com.parithidb.ljassignment.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database entity representing a GitHub repository.
 *
 * @property id Repository ID (Primary Key)
 * @property repoName Name of the repository
 * @property repoUrl HTML URL of the repository
 * @property ownerName Repository owner username
 * @property imageUrl Owner avatar URL
 */

@Entity(tableName = "REPOS")
data class RepoEntity(
    @PrimaryKey val id: Long,
    val repoName: String,
    val repoUrl: String,
    val ownerName: String,
    val imageUrl: String
)