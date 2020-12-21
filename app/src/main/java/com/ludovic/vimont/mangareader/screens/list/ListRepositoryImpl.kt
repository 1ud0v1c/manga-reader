package com.ludovic.vimont.mangareader.screens.list

import com.ludovic.vimont.mangareader.api.JikanAPI
import com.ludovic.vimont.mangareader.db.MangaDao
import com.ludovic.vimont.mangareader.entities.JikanResponse
import com.ludovic.vimont.mangareader.entities.Manga
import retrofit2.Response

class ListRepositoryImpl(private val jikanAPI: JikanAPI,
                         private val mangaDao: MangaDao): ListRepository {
    override suspend fun list(): List<Manga> {
        val cachedPhotos: List<Manga> = mangaDao.getAll()
        if (cachedPhotos.isNotEmpty()) {
            return cachedPhotos
        }
        return fetchFromJikanAPI()
    }

    private suspend fun fetchFromJikanAPI(): ArrayList<Manga> {
        val mangas = ArrayList<Manga>()
        try {
            val response: Response<JikanResponse> = jikanAPI.getPopularMangas()
            if (response.isSuccessful) {
                response.body()?.let { jikanResponse: JikanResponse ->
                    val topMangs: List<Manga> = jikanResponse.top
                    mangas.addAll(topMangs)
                    mangaDao.insert(topMangs)
                }
            }
        } catch (exception: Exception) {
            println(exception.message.toString())
        }
        return mangas
    }
}