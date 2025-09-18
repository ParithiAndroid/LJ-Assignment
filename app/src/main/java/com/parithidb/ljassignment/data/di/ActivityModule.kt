package com.parithidb.ljassignment.data.di

import com.parithidb.ljassignment.data.api.GithubRepoApiService
import com.parithidb.ljassignment.data.database.AppDatabase
import com.parithidb.ljassignment.data.repository.ReposRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

/**
 * Hilt module to provide dependencies tied to Activity lifecycle.
 * Provides ReposRepository as a scoped singleton per activity.
 */

@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityModule {

    @Provides
    @ActivityRetainedScoped
    fun provideReposRepository(
        database: AppDatabase,
        api: GithubRepoApiService
    ): ReposRepository {
        return ReposRepository(database, api)
    }

}