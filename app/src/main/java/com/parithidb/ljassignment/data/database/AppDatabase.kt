package com.parithidb.ljassignment.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.parithidb.ljassignment.data.database.entities.RepoEntity
import com.parithidb.ljassignment.data.database.dao.RepoDao

/**
 * Room database for the application.
 * Holds repository data and exposes DAO for access.
 */

@Database(
    entities = [RepoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Accessor for repository DAO.
     */

    abstract fun repoDao(): RepoDao

    /**
     * Clear all tables in the database.
     * Useful for full reset or testing purposes.
     */

    suspend fun clearDatabase() {
        clearAllTables()
    }
}