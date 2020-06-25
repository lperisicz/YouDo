package com.perisic.luka.data.remote.model.response

import com.perisic.luka.data.remote.model.helper.User

data class PostResponse(
    val user: User,
    val id: Int,
    val title: String,
    val description: String,
    val locationString: String?,
    val noLocation: Int,
    val priceAmount: Float,
    val priceCurrency: String,
    val priceMode: String
)