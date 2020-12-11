package com.ludovic.vimont.mangareader.entities

import com.squareup.moshi.Json
import java.util.*

data class Manga(@field:Json(name = "mal_id")
                 val id: String,
                 val title: String,
                 val url: String,
                 @field:Json(name = "start_date")
                 val startDate: String,
                 @field:Json(name = "end_date")
                 val endDate: String,
                 val score: Float,
                 val volumes: Int?,
                 @field:Json(name = "image_url")
                 val cover: String) {
    fun getWebTitle(): String {
        val alphanumericOnlyRegex = Regex("[^A-Za-z0-9 ]")
        val result = alphanumericOnlyRegex.replace(title, "").replace(" ", "-")
        return result.toLowerCase(Locale.getDefault())
    }
}