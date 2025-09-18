package com.parithidb.ljassignment.data.di

import android.content.Context
import com.parithidb.ljassignment.data.api.GithubRepoApiService
import com.parithidb.ljassignment.data.api.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module to provide application-wide singleton dependencies.
 */

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    /**
     * Provides the application context.
     */

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    /**
     * Provides GitHub API service using Retrofit client.
     */

    @Provides
    @Singleton
    fun provideGithubRepoApiService(
        retrofitClient: RetrofitClient
    ): GithubRepoApiService {
        return retrofitClient.getGithubRepoApiService()
    }

}