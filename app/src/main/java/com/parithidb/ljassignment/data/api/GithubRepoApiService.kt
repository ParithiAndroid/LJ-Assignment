package com.parithidb.ljassignment.data.api

import com.parithidb.ljassignment.data.api.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API service for fetching GitHub repositories.
 */

interface GithubRepoApiService {

    /**
     * Fetch popular Swift repositories sorted by stars.
     *
     * @param q Query string, defaults to "language:swift"
     * @param sort Sort criteria, defaults to "stars"
     * @param order Sort order, defaults to "desc"
     * @return [SearchResponse] containing list of repositories
     */

    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") q: String = "language:swift",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): SearchResponse
}