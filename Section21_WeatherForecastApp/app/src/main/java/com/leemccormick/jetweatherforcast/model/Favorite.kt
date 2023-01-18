package com.leemccormick.jetweatherforcast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_tbl")
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name = "city") // If it is the same name, we can do like --> @ColumnInfo
    val city: String,

    @ColumnInfo(name = "country")
    val country: String
)
