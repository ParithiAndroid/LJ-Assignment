package com.parithidb.ljassignment.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.parithidb.ljassignment.data.database.entities.RepoEntity
import com.parithidb.ljassignment.data.database.dao.RepoDao

@Database(
    entities = [RepoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao

   suspend fun clearDatabase() {
        clearAllTables()
    }
}