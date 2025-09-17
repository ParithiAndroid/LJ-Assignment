package com.parithidb.ljassignment.data.repository

import androidx.room.withTransaction
import com.parithidb.ljassignment.data.api.GithubRepoApiService
import com.parithidb.ljassignment.data.api.model.toEntity
import com.parithidb.ljassignment.data.database.AppDatabase
import com.parithidb.ljassignment.data.database.entities.RepoEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReposRepository @Inject constructor(
    private val database: AppDatabase,
    private val api: GithubRepoApiService
) {
    private val repoDao = database.repoDao()

    fun getRepos(): Flow<List<RepoEntity>> = repoDao.getAllRepos()

    suspend fun refreshRepos(): Result<Unit> {
        return try {
            val response = api.searchRepos()

            val entities = response.items.map { it.toEntity() }

            database.withTransaction {
                repoDao.clearAll()
                repoDao.insertAllRepos(entities)
            }

            Result.success(Unit)
        } catch (e: HttpException) {
            // API returned non-2xx response
            Result.failure(Exception("API error: ${e.code()} ${e.message()}"))
        } catch (e: IOException) {
            // Network issue (no internet, timeout)
            Result.failure(Exception("Network error: ${e.message}"))
        } catch (e: Exception) {
            // Anything else (parsing, unexpected)
            Result.failure(e)
        }
    }
}
