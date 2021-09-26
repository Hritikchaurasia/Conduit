package com.example.conduit_kotlin_app.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Article(
    val author: Author = Author(),
    val body: String = "",
    val createdAt: String = "",
    val description: String = "",
    val favorited: Boolean = false,
    val favoritesCount: Int? = 0,
    val slug: String? = "",
    val tagList: List<String>? = listOf(),
    val title: String = "",
    val updatedAt: String? = ""
):Parcelable{

}