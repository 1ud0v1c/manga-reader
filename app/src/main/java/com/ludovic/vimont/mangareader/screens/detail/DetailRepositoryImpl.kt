package com.ludovic.vimont.mangareader.screens.detail

import com.ludovic.vimont.mangareader.api.FileDownloader
import com.ludovic.vimont.mangareader.api.JikanAPI
import com.ludovic.vimont.mangareader.api.MangaReaderAPI
import com.ludovic.vimont.mangareader.db.MangaDao
import com.ludovic.vimont.mangareader.entities.*
import org.jsoup.HttpStatusException
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import retrofit2.Response
import kotlin.collections.ArrayList

class DetailRepositoryImpl(private val jikanAPI: JikanAPI,
                           private val mangaDao: MangaDao,
                           private val fileDownloader: FileDownloader): DetailRepository {
    override suspend fun fetchMangaContent(mangaId: String): ReadingPage {
        var manga: FullManga? = null
        val response: Response<FullManga> = jikanAPI.getManga(mangaId)
        if (response.isSuccessful) {
            response.body()?.let { fetchManga: FullManga ->
                manga = fetchManga
            }
        }

        manga?.let {
            val isFavorite = mangaDao.get(mangaId).isFavorite
            val document: Document = getDocument(manga)
            val tables: Elements = document.select("table")
            if (tables.isNotEmpty()) {
                val chapters = getChapters(tables.last())
                return ReadingPage(
                    it.id, it.title, it.synopsis, it.published.from, it.status,
                    it.authors.first().name, it.genres.map { genre: Genre -> genre.name }, chapters,
                    isFavorite
                )
            }
        }

        return ReadingPage("", "", "", "", "", "", ArrayList(), ArrayList())
    }

    private fun getDocument(manga: FullManga?): Document {
        manga?.let {
            return try {
                // We can have issues with the name used on MAL compare to MangaReader or MangaPanda
                // First, we try with the default title
                MangaReaderAPI.getManga(manga.getWebTitle())
            } catch (exception: HttpStatusException) {
                try {
                    // Then, with the english title, can be useful for Manga like "Haikyuu' on MAL
                    // Instead of Haikyu on MangaReader
                    MangaReaderAPI.getManga(manga.getWebTitle(manga.englishTitle))
                } catch (exception: HttpStatusException) {
                    Document.createShell("")
                }
            }
        }
        return Document.createShell("")
    }

    private fun getChapters(table: Element): ArrayList<LinkChapter> {
        val chapters = ArrayList<LinkChapter>()
        val trs: Elements = table.select("tr")
        trs.removeFirst()
        for (tr: Element in trs) {
            val tds: Elements = tr.select("td")
            val ahref: Element = tds[0].selectFirst("a")
            val addedDate: String = tds[1].text()
            val link = ahref.attr("href").removeRange(0, 1)
            chapters.add(
                LinkChapter(
                    ahref.text(),
                    addedDate,
                    MangaReaderAPI.buildLink(link)
                )
            )
        }
        return chapters
    }

    override suspend fun downloadChapters(chapters: List<LinkChapter>) {
        for (chapter in chapters) {
            println(chapter)
            try {
                val document: Document = MangaReaderAPI.getDocument(chapter.link)
                val scripts: Elements = document.select("script")
                if (scripts.size > 1) {
                    val jsonContent: String = scripts[1].data().replace("document[\"mj\"]=", "")
                    MangaReaderAPI.convertJsonToChapter(jsonContent)?.let { chapter: Chapter ->
                        for (image in chapter.images) {
                            try {
                                val bitmap = fileDownloader.downloadBitmap(image.getURL())
                                fileDownloader.saveImage(bitmap, "${chapter.name}/${chapter.currentChapter}","${image.page}")
                            } catch(e: Exception) {
                                println("Exception for ${image.getURL()}: ${e.message}")
                            }
                        }
                    }
                }
            } catch(e: Exception) {
                println("Exception for ${chapter}: ${e.message}")
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