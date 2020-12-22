package com.ludovic.vimont.mangareader.entities

import com.squareup.moshi.Json

data class ChapterPage(@field:Json(name = "p")
                       val page: Int,
                       @field:Json(name = "u")
                       var link: String)