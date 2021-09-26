package com.example.conduit_kotlin_app.repositories

import com.example.conduit_kotlin_app.api.ConduitApi
import com.example.conduit_kotlin_app.data.Article
import com.example.conduit_kotlin_app.data.ListArticles
import com.example.conduit_kotlin_app.data.User
import com.example.conduit_kotlin_app.data.UserResponse
import javax.inject.Inject

class MainRepository @Inject constructor(private val conduitApi: ConduitApi) {

    //signup
    suspend fun signUp(
        username: String,
        email: String,
        password: String

    ) = conduitApi.signUp(
        UserResponse(
            User(
                email = email,
                password = password,
                username = username
            )
        )
    )

    //login
    suspend fun login(
        email: String,
        password: String
    ) = conduitApi.login(
        UserResponse(
            User(
                email = email,
                password = password

            )
        )
    )

    //get articles
    suspend fun getListArticles() = conduitApi.getListArticles()

    //user-feed
    suspend fun userfeed(header: String) = conduitApi.userfeed(
        header = header
    )

    //my-articles
    suspend fun getMyarticles(
        author:String
    ) = conduitApi.getMyarticles(
        author = author
    )

    //post article
    suspend fun postArticle(
        header: String,
        article: Article
    ) = conduitApi.postArticle(
        header = header,
        article = article
    )

    // follow user
    suspend fun followOtherUser(
        header: String,
        username: String
    )= conduitApi.followOtherUser(
        header = header,
        username = username
    )

    //delete article
    suspend fun deleteArticle(
        header: String,
        slug:String
    ) = conduitApi.deleteArticle(
        header = header,
        slug = slug
    )

    //add article to favorite
    suspend fun addArticleToFavorite(
        header: String,
        slug:String
    ) = conduitApi.addArticleToFavorite(
        header = header,
        slug = slug
    )
}