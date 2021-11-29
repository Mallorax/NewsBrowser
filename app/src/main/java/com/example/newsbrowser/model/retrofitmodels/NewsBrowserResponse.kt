package com.example.newsbrowser.model.retrofitmodels

data class NewsBrowserResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)