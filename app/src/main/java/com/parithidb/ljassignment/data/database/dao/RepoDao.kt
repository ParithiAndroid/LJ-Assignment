package com.parithidb.ljassignment.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.parithidb.ljassignment.data.database.entities.RepoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for [RepoEntity].
 * Handles database operations such as insert, query, and delete.
 */

@Dao
interface RepoDao {
    /**
     * Fetch all repositories from local database in descending order of ID.
     * Returns a Flow to observe live updates from the database.
     */

    @Query("SELECT * FROM REPOS ORDER BY id DESC")
    fun getAllRepos(): Flow<List<RepoEntity>>

    /**
     * Insert list of repositories into the database.
     * If a conflict occurs (same primary key), replace the existing entry.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRepos(repos: List<RepoEntity>)

    /**
     * Delete all repositories from the database.
     */

    @Query("DELETE FROM REPOS")
    suspend fun clearAll()
}