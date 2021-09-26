package com.example.conduit_kotlin_app.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun convertDateAndTime(date: String): String {
    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val output = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss")

    var d: Date? = null
    try {
        d = input.parse(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return output.format(d)

}