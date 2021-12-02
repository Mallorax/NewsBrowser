package com.example.newsbrowser.repository

import com.example.newsbrowser.model.ArticleAppModel
import com.example.newsbrowser.model.NewsRequest
import com.example.newsbrowser.rest.BreakingNewsRetrofitEndpoint
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ArticlesPagingSource @Inject constructor(
    private val newsApi: BreakingNewsRetrofitEndpoint
) : BreakingNewsPagingSource(newsApi) {

    //set default request
    private var request: NewsRequest = NewsRequest()

    fun setRequest(request: NewsRequest){
         this.request = request
     }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ArticleAppModel>> {
        val nextPageNumber = params.key ?: 1
        return if (request.query.isNullOrEmpty()) {
            newsApi.getArticles(page = nextPageNumber,
                sortBy = request.sortOrder.order,
                language = request.language.lang)
                .subscribeOn(Schedulers.computation())
                .map { response -> toLoadResult(response, nextPageNumber) }
                .onErrorReturn { t -> LoadResult.Error(t) }
        } else {
            newsApi.getArticlesWithQ(
                query = request.query!!,
                page = nextPageNumber,
                sortBy = request.sortOrder.order,
                language = request.language.lang
            )
                .subscribeOn(Schedulers.computation())
                .map { response -> toLoadResult(response, nextPageNumber) }
                .onErrorReturn { t -> LoadResult.Error(t) }

        }
    }

}