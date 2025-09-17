package com.parithidb.ljassignment.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.parithidb.ljassignment.data.database.entities.RepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {
    @Query("SELECT * FROM REPOS ORDER BY id DESC")
    fun getAllRepos(): Flow<List<RepoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRepos(repos: List<RepoEntity>)

    @Query("SELECT * FROM REPOS WHERE repoName LIKE :query OR ownerName LIKE :query OR id LIKE :query ORDER BY id DESC")
    fun search(query: String): Flow<List<RepoEntity>>

    @Query("DELETE FROM REPOS")
    suspend fun clearAll()

}