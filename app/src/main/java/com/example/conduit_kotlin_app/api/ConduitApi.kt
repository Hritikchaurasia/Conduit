package com.example.conduit_kotlin_app.api

import com.example.conduit_kotlin_app.data.Article
import com.example.conduit_kotlin_app.data.Author
import com.example.conduit_kotlin_app.data.ListArticles
import com.example.conduit_kotlin_app.data.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface ConduitApi {

    companion object{
        const val BASE_URL = "https://conduit.productionready.io/api/"

    }

    //sign up
    @POST("users")
    suspend fun signUp(
       @Body users: UserResponse
    ):Response<UserResponse>

    //log in
    @POST("users/login")
    suspend fun login(
        @Body users: UserResponse
    ):Response<UserResponse>


    //get articles
    @GET("articles")
    suspend fun getListArticles(): Response<ListArticles>


    //user-feed
    @GET("articles/feed")
    suspend fun userfeed(
        @Header("Authorization") header : String
    ):Response<ListArticles>

    //my-articles
    @GET("articles")
    suspend fun getMyarticles(
        @Query("author") author:String
    ): Response<ListArticles>

    //post article
    @POST("articles")
    suspend fun postArticle(
        @Header("Authorization") header : String,
        @Body article: Article
    ):Response<Article>

    //follow other-user
    @POST("profiles/{username}/follow")
    suspend fun followOtherUser(
        @Header("Authorization")header : String,
        @Path("username") username:String
    ):Response<Author>

    //delete article
    @DELETE("articles/{slug}")
    suspend fun deleteArticle(
        @Header("Authorization")header : String,
        @Path("slug") slug:String
    )

    //add article to favorite
    @POST("articles/{slug}/favorite")
    suspend fun addArticleToFavorite(
        @Header("Authorization")header : String,
        @Path("slug") slug:String
    ):Response<Article>
}