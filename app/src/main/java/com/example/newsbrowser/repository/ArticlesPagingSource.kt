package com.example.newsbrowser.repository

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.example.newsbrowser.model.ArticleAppModel
import com.example.newsbrowser.model.mapNetModelToAppModel
import com.example.newsbrowser.model.retrofitmodels.NewsBrowserResponse
import com.example.newsbrowser.rest.BreakingNewsRetrofitEndpoint
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ArticlesPagingSource @Inject constructor(
    private val newsApi: BreakingNewsRetrofitEndpoint): RxPagingSource<Int, ArticleAppModel>() {

    override fun getRefreshKey(state: PagingState<Int, ArticleAppModel>): Int? {
        val anchorPos = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPos) ?: return null
        val prevKey = anchorPage.prevKey
        if (prevKey != null){
            return prevKey + 1
        }
        val nextKey = anchorPage.nextKey
        if (nextKey != null){
            return nextKey - 1
        }

        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ArticleAppModel>> {
        var nextPageNumber = params.key
        if (nextPageNumber == null){
            nextPageNumber = 1
        }

        return newsApi.getDefaultTopHeadlines(page = nextPageNumber)
            .subscribeOn(Schedulers.computation())
            .map{response -> toLoadResult(response, nextPageNumber)}
            .onErrorReturn{t -> LoadResult.Error(t)}
    }

    private fun toLoadResult(response: NewsBrowserResponse, page: Int): LoadResult<Int, ArticleAppModel> {
        return LoadResult.Page(
            response.articles.map { t -> mapNetModelToAppModel(t) },
            null,
            page,
            LoadResult.Page.COUNT_UNDEFINED,
            LoadResult.Page.COUNT_UNDEFINED
        )
    }
}


