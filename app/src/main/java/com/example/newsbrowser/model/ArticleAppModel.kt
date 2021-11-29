package com.example.newsbrowser.model


data class ArticleAppModel(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val sourceName: String,
    var isFavourite: Boolean = false
) {
}