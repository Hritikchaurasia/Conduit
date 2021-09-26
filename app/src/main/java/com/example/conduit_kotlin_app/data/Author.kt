package com.example.conduit_kotlin_app.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


//sub class of article
@Parcelize
data class Author(
    val bio: String = "",
    val following: Boolean = false,
    val image: String = "",
    val username: String = ""
): Parcelable {

}

