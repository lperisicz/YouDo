package com.perisic.luka.data.remote.model.response

data class LoginResponse(
    val token: String,
    val refreshToken: String
)