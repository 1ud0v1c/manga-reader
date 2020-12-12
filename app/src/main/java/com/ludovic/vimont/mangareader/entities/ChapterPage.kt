package com.ludovic.vimont.mangareader.entities

import com.squareup.moshi.Json

data class ChapterPage(@field:Json(name = "p")
                       val page: Int,
                       @field:Json(name = "u")
                       val link: String,
                       @field:Json(name = "h")
                       val height: Int,
                       @field:Json(name = "w")
                       val width: Int)