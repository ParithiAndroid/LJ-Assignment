package com.parithidb.ljassignment.data.api

import android.content.Context
import com.parithidb.ljassignment.util.Common
import com.parithidb.ljassignment.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import javax.inject.Inject

/**
 * Singleton Retrofit client for GitHub API calls.
 * Sets up logging, connectivity check, and JSON converters.
 */

class RetrofitClient @Inject constructor(context: Context) {
    private val retrofit: Retrofit
    private var githubRepoApiService: GithubRepoApiService? = null

    companion object {
        @Volatile
        private var retrofitClient: RetrofitClient? = null

        fun getInstance(context: Context): RetrofitClient {
            return retrofitClient ?: synchronized(this) {
                retrofitClient ?: RetrofitClient(context).also { retrofitClient = it }
            }
        }
    }

    init {
        // Logging interceptor for debugging API calls
        val loggingInterceptor = HttpLoggingInterceptor()
        val BASE_URL: String = Constants.GITHUB_SERVER
        if (Constants.DEVELOPMENT_MODE) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        // OkHttpClient with logging and connectivity interceptor
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ConnectivityInterceptor(context))
            .build()

        // Retrofit instance with scalar and Gson converters
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Returns GitHub API service instance.
     * Lazily creates it if not already created.
     */

    fun getGithubRepoApiService(): GithubRepoApiService {
        return githubRepoApiService ?: retrofit.create(GithubRepoApiService::class.java).also {
            githubRepoApiService = it
        }
    }

    /**
     * Interceptor to check network connectivity before executing requests.
     */

    class ConnectivityInterceptor(private val context: Context) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            if (!Common.isInternetConnected(context)) {
                throw NoConnectivityException()
            }

            val builder = chain.request().newBuilder()
            return chain.proceed(builder.build())
        }
    }

    /**
     * Exception thrown when there is no internet connection.
     */

    class NoConnectivityException : IOException() {
        override val message: String
            get() = "No connectivity exception"
    }

}