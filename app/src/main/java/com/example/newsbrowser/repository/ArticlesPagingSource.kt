package com.example.newsbrowser.repository

import com.example.newsbrowser.model.ArticleAppModel
import com.example.newsbrowser.model.NewsRequest
import com.example.newsbrowser.rest.BreakingNewsRetrofitEndpoint
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
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
        // API returns an error when there are too many results,
        // so I decided to include only results from last month
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Calendar.getInstance()
        date.add(Calendar.MONTH, -1)
        val dateString = dateFormat.format(date.time)
        return if (request.query.isNullOrEmpty()) {
            newsApi.getArticles(page = nextPageNumber,
                sortBy = request.sortOrder.order,
                language = request.language.lang,
                from = dateString)
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