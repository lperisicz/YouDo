package com.perisic.luka.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokens")
internal class Token(
    @PrimaryKey val id: Int = 1,
    val accessToken: String,
    val refreshToken: String
)