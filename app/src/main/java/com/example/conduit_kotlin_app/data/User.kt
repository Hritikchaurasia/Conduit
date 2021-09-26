package com.example.conduit_kotlin_app.data

data class User(
    val bio: String? = "",
    val email: String = "",
    val image: Any? = Any(),
    val token: String = "",
    val username: String = "",
    val password:String? = ""
)