package com.parithidb.ljassignment.data.di

import android.content.Context
import androidx.room.Room
import com.parithidb.ljassignment.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module to provide Room database as a singleton.
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides AppDatabase singleton instance.
     */

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "github_repo_database"
        )
            .fallbackToDestructiveMigration()  // Fallback to destructive migration for simplicity
            .addMigrations()
            .build()
    }
}