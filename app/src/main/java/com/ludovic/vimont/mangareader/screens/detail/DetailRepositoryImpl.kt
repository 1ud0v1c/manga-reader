package com.ludovic.vimont.mangareader.screens.detail

import com.ludovic.vimont.mangareader.api.images.FileDownloader
import com.ludovic.vimont.mangareader.api.JikanAPI
import com.ludovic.vimont.mangareader.api.MangaAPI
import com.ludovic.vimont.mangareader.db.MangaDao
import com.ludovic.vimont.mangareader.entities.*
import retrofit2.Response
import kotlin.collections.ArrayList

class DetailRepositoryImpl(private val jikanAPI: JikanAPI,
                           private val mangaAPI: MangaAPI,
                           private val mangaDao: MangaDao,
                           private val fileDownloader: FileDownloader
): DetailRepository {
    override suspend fun fetchMangaContent(mangaId: String): ReadingPage {
        var manga: FullManga? = null
        val response: Response<FullManga> = jikanAPI.getManga(mangaId)
        if (response.isSuccessful) {
            response.body()?.let { fetchManga: FullManga ->
                manga = fetchManga
            }
        }
        manga?.let { fullManga ->
            mangaAPI.fromMangaToReadingPage(fullManga)?.let {
                return it
            }
        }
        return ReadingPage("", "", "", "", "", "", ArrayList(), ArrayList())
    }

    override suspend fun downloadChapters(chapters: List<LinkChapter>) {
        for (linkChapter in chapters) {
            mangaAPI.fromLinkChapterToChapter(linkChapter.link)?.let { chapter: Chapter ->
                for (image in chapter.images) {
                    try {
                        val bitmap = fileDownloader.downloadBitmap(image.link)
                        fileDownloader.saveImage(bitmap, "${chapter.name}/${chapter.currentChapter}","${image.page}")
                    } catch(e: Exception) {
                        println("Exception for ${image.link}: ${e.message}")
                    }
                }
            }
        }
    }

    override suspend fun addToFavorite(mangaId: String) {
        mangaDao.updateFavorite(mangaId, true)
    }

    override suspend fun removeFromFavorite(mangaId: String) {
        mangaDao.updateFavorite(mangaId, false)
    }
}