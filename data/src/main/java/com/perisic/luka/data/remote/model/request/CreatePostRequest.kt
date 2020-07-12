package com.perisic.luka.data.remote.model.request

class CreatePostRequest(
    val title: String?,
    val description: String?,
    val priceAmount: Double?,
    val priceCurrency: String = "HRK",
    val priceMode: String = "full",
    val latitude: Double?,
    val longitude: Double?,
    val noLocation: Boolean
)