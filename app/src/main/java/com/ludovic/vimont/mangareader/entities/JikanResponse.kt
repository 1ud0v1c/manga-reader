package com.ludovic.vimont.mangareader.entities

import com.squareup.moshi.Json

data class JikanResponse(
    @field:Json(name = "request_hash")
    val hash: String,
    @field:Json(name = "request_cached")
    val cached: Boolean,
    @field:Json(name = "request_cache_expiry")
    val cacheExpiry: Int,
    val top: List<Manga>
)