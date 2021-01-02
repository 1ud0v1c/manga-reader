package com.ludovic.vimont.mangareader.screens.list

import com.ludovic.vimont.mangareader.api.JikanAPI
import com.ludovic.vimont.mangareader.db.MangaDao
import com.ludovic.vimont.mangareader.entities.JikanResponse
import com.ludovic.vimont.mangareader.entities.Manga
import retrofit2.Response

class ListRepositoryImpl(private val jikanAPI: JikanAPI,
                         private val mangaDao: MangaDao): ListRepository {
    override suspend fun list(page: Int): List<Manga> {
        val cachedMangas: List<Manga> = mangaDao.getAll()
        if (cachedMangas.isEmpty()) {
            return fetchFromJikanAPI(page)
        }
        if (cachedMangas.size >= page * JikanAPI.ITEMS_PER_PAGE) {
            return cachedMangas.subList(0, page * JikanAPI.ITEMS_PER_PAGE)
        }
        val newMangasToLoad = fetchFromJikanAPI(page)
        newMangasToLoad.addAll(0, cachedMangas)
        return newMangasToLoad
    }

    private suspend fun fetchFromJikanAPI(page: Int): ArrayList<Manga> {
        val mangas = ArrayList<Manga>()
        try {
            val response: Response<JikanResponse> = jikanAPI.getPopularMangas(page)
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