package com.ludovic.vimont.mangareader.entities

import com.squareup.moshi.Json

data class Chapter(@field:Json(name = "cn")
                   val currentChapter: Int,
                   @field:Json(name = "im")
                   val images: List<ChapterPage>,
                   @field:Json(name = "mn")
                   val name: String,
                   @field:Json(name = "pl")
                   val previous: String,
                   @field:Json(name = "nl")
                   val next: String,
                   @field:Json(name = "su")
                   val slug: String)