package com.parithidb.ljassignment.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "REPOS")
data class RepoEntity(
    @PrimaryKey val id: Long,
    val repoName: String,
    val repoUrl: String,
    val ownerName: String,
    val imageUrl: String
)