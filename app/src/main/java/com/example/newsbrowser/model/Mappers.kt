package com.example.newsbrowser.model

import com.example.newsbrowser.model.retrofitmodels.Article

fun mapNetModelToAppModel(article: Article): ArticleAppModel {
    return ArticleAppModel(
        article.author.orEmpty(),
        article.content.orEmpty(),
        article.description.orEmpty(),
        article.publishedAt,
        article.title,
        article.url,
        article.urlToImage.orEmpty(),
        article.source.name
    )
}