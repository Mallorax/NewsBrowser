package com.example.newsbrowser.rest

import com.example.newsbrowser.BuildConfig
import com.example.newsbrowser.model.retrofitmodels.NewsBrowserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BreakingNewsRetrofitEndpoint {

    @GET("top-headlines")
    fun getDefaultTopHeadlines(
        @Query("country")country: String = "us",
        @Query("apiKey")apiKey: String = BuildConfig.API_KEY,
        @Query("pageSize")pageSize: Int = 10,
        @Query("page")page: Int = 1
    ): Single<NewsBrowserResponse>

    @GET("everything")
    fun getArticles(
        @Query("language")language: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey")apiKey: String = BuildConfig.API_KEY,
        @Query("pageSize")pageSize: Int = 10,
        @Query("page")page: Int = 1
    ): Single<NewsBrowserResponse>

    @GET("everything")
    fun getArticlesWithQ(
        @Query("q") query: String,
        @Query("language")language: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey")apiKey: String = BuildConfig.API_KEY,
        @Query("pageSize")pageSize: Int = 10,
        @Query("page")page: Int = 1
    ): Single<NewsBrowserResponse>
}