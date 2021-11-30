package com.example.newsbrowser.rest

import com.example.newsbrowser.BuildConfig
import com.example.newsbrowser.model.retrofitmodels.NewsBrowserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface BreakingNewsRetrofitEndpoint {

    @GET("v2/top-headlines")
    fun getDefaultTopHeadlines(
        country: String = "us",
        apiKey: String = BuildConfig.API_KEY,
        pageSize: Int = 10,
        page: Int = 1
    ): Single<NewsBrowserResponse>
}