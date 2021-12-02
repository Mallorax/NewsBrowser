package com.example.newsbrowser.ui.breaking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import androidx.paging.rxjava3.flowable
import com.example.newsbrowser.model.ArticleAppModel
import com.example.newsbrowser.model.NewsRequest
import com.example.newsbrowser.repository.ArticlesPagingSource
import com.example.newsbrowser.repository.BreakingNewsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(private val breakingNewsRepo: BreakingNewsPagingSource,
                                                private val articlesRepo: ArticlesPagingSource
) : ViewModel() {

    private val pager = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = true,
            prefetchDistance = 5,
            initialLoadSize = 10
        ),
        pagingSourceFactory = { breakingNewsRepo }
    ).flowable

    fun getBreakingNews(): Flowable<PagingData<ArticleAppModel>>{
        return pager.cachedIn(viewModelScope)
    }
    fun getArticles(newsRequest: NewsRequest = NewsRequest()): Flowable<PagingData<ArticleAppModel>>{
        articlesRepo.setRequest(newsRequest)
        return Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { articlesRepo }
        ).flowable.cachedIn(viewModelScope)
    }
}