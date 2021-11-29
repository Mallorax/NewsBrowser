package com.example.newsbrowser.hilt

import com.example.newsbrowser.rest.BreakingNewsRetrofitEndpoint
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit(): BreakingNewsRetrofitEndpoint {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(BreakingNewsRetrofitEndpoint::class.java)

    }
}