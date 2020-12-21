package com.ludovic.vimont.mangareader.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*

@Entity
data class Manga(@PrimaryKey
                 @field:Json(name = "mal_id")
                 val id: String,
                 val title: String,
                 val url: String,
                 @field:Json(name = "start_date")
                 val startDate: String,
                 @field:Json(name = "end_date")
                 val endDate: String?,
                 val score: Float,
                 val volumes: Int?,
                 @field:Json(name = "image_url")
                 val cover: String,
                 var isFavorite: Boolean = false)