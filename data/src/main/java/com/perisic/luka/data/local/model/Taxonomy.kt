package com.perisic.luka.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taxonomies")
data class Taxonomy(
    @PrimaryKey val id: Int,
    val title: String,
    val type: String,
    var selected: Boolean = false
)