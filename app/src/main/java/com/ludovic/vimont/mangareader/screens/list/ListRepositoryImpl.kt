package com.ludovic.vimont.mangareader.screens.list

import com.ludovic.vimont.mangareader.api.JikanAPI
import com.ludovic.vimont.mangareader.entities.JikanResponse
import com.ludovic.vimont.mangareader.entities.Manga
import retrofit2.Response

class ListRepositoryImpl(private val jikanAPI: JikanAPI): ListRepository {
    override suspend fun list(): List<Manga> {
        val mangas = ArrayList<Manga>()
        try {
            val response: Response<JikanResponse> = jikanAPI.getPopularMangas()
            if (response.isSuccessful) {
                response.body()?.let { jikanResponse: JikanResponse ->
                    mangas.addAll(jikanResponse.top)
                }
            }
        } catch (exception: Exception) {
            println(exception.message.toString())
        }
        return mangas
    }
}