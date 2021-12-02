package com.example.newsbrowser.model

class NewsRequest constructor(
    var query: String? = null,
    var language: Language = Language.ENGLISH,
    var sortOrder: SortOrder = SortOrder.PUBLICATION_DATE
){
    enum class Language(val lang: String){
        ARABIC("ar"),
        GERMAN("de"),
        ENGLISH("en"),
        SPANISH("es"),
        FRENCH("fr"),
        HEBREW("he"),
        ITALIAN("it"),
        DUTCH("nl"),
        NORWEGIAN("no"),
        PORTUGUESE("pt"),
        POLISH("pl"),
        RUSSIAN("ru")
    }

    enum class SortOrder(val order: String){
        RELEVANCY("relevancy"),
        POPULARITY("popularity"),
        PUBLICATION_DATE("publishedAt")
    }

}