package com.ludovic.vimont.mangareader.entities

import com.squareup.moshi.Json

data class Author(@field:Json(name = "mal_id")
                  val id: String,
                  val name: String,
                  val url: String)