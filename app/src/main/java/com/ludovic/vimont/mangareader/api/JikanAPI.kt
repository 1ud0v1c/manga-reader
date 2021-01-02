package com.ludovic.vimont.mangareader.api

import com.ludovic.vimont.mangareader.entities.FullManga
import com.ludovic.vimont.mangareader.entities.JikanResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @see https://jikan.docs.apiary.io
 */
interface JikanAPI {
    companion object {
        const val BASE_URL = "https://api.jikan.moe/v3/"
        const val ITEMS_PER_PAGE = 50
    }

    /**
     * Top items on MyAnimeList
     * @page: the Top page on MyAnimeList is paginated offers 50 items per page
     * @subtype returns a filtered top list of a type (anime manga, people, characters) item.
     */
    @GET("top/manga/{page}/{type}")
    suspend fun getPopularMangas(@Path("page") page: Int = 1,
                                 @Path("type") type: String = "bypopularity"): Response<JikanResponse>

    @GET("manga/{manga_id}")
    suspend fun getManga(@Path("manga_id") mangaId: String): Response<FullManga>
}