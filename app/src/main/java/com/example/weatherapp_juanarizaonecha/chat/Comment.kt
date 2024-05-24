package com.example.weatherapp_juanarizaonecha.chat

data class Comment(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val message: String = "",
    val hour: String = "",
    var isCurrentUser: Boolean = false
)
