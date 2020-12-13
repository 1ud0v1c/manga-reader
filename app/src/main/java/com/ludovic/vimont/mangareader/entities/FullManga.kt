package com.ludovic.vimont.mangareader.entities

import com.squareup.moshi.Json
import java.util.Locale

data class FullManga(@field:Json(name = "mal_id")
                 val id: String,
                 val title: String,
                 @field:Json(name = "title_english")
                 val englishTitle: String,
                 @field:Json(name = "title_japanese")
                 val japaneseTitle: String,
                 @field:Json(name = "image_url")
                 val cover: String,
                 val synopsis: String,
                 val background: String,
                 val url: String,
                 val volumes: Int?,
                 val authors: List<Author>,
                 val score: Float,
                 @field:Json(name = "scored_by")
                 val scoredBy: Int,
                 val status: String,
                 val published: Published,
                 val genres: List<Genre>) {
    fun getWebTitle(title: String = this.title): String {
        val alphanumericOnlyRegex = Regex("[^A-Za-z0-9 ]")
        val result = alphanumericOnlyRegex.replace(title, "").replace(" ", "-")
        return result.toLowerCase(Locale.getDefault())
    }
}