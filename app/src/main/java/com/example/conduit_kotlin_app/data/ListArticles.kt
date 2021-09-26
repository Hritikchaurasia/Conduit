package com.example.conduit_kotlin_app.data

data class ListArticles(
    val articles: List<Article> = listOf(),
    val articlesCount: Int? = 0
)