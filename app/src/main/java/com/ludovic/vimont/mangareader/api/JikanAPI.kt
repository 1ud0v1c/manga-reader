package com.ludovic.vimont.mangareader.api

import com.ludovic.vimont.mangareader.entities.FullManga
import com.ludovic.vimont.mangareader.entities.JikanResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @see https://jikan.moe/
 */
interface JikanAPI {
    companion object {
        const val BASE_URL = "https://api.jikan.moe/v3/"
    }

    @GET("top/manga/1/bypopularity")
    suspend fun getPopularMangas(): Response<JikanResponse>

    // TODO: response entity ?
    @GET("manga/{manga_id}")
    suspend fun getManga(@Path("manga_id") mangaId: String): Response<FullManga>
}