package com.example.newsbrowser.hilt

import androidx.lifecycle.ViewModel
import com.example.newsbrowser.repository.ArticlesPagingSource
import com.example.newsbrowser.rest.BreakingNewsRetrofitEndpoint
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Inject
    @Singleton
    @ViewModelScoped
    fun provideBreakingNewsDataSource(newsApi: BreakingNewsRetrofitEndpoint): ArticlesPagingSource{
        return ArticlesPagingSource(newsApi)
    }
}