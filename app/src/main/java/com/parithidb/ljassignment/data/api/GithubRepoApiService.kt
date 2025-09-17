package com.parithidb.ljassignment.data.api

import com.parithidb.ljassignment.data.api.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepoApiService {
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") q: String = "language:swift",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): SearchResponse
}