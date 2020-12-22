package com.ludovic.vimont.mangareader.api.manga

import com.ludovic.vimont.mangareader.api.MangaAPI
import com.ludovic.vimont.mangareader.db.MangaDao
import com.ludovic.vimont.mangareader.entities.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * https://www.mangareader.net/
 */
class MangaReaderAPI(private val mangaDao: MangaDao): MangaAPI {
    companion object {
        private const val BASE_URL = "https://www.mangareader.net/"

        fun getManga(mangaTitle: String): Document {
            return getDocument(BASE_URL + mangaTitle)
        }

        fun getDocument(url: String): Document {
            return Jsoup.connect(url).get()
        }

        fun buildLink(path: String): String {
            return BASE_URL + path
        }

        fun convertJsonToChapter(jsonContent: String): Chapter? {
            val moshi = Moshi.Builder().build()
            val type = Types.newParameterizedType(Chapter::class.java)
            val adapter = moshi.adapter<Chapter>(type)
            return adapter.fromJson(jsonContent)
        }
    }

    override suspend fun fromMangaToReadingPage(fullManga: FullManga): ReadingPage? {
        val isFavorite = mangaDao.get(fullManga.id).isFavorite
        val document: Document = getDocument(fullManga)
        val tables: Elements = document.select("table")
        if (tables.isNotEmpty()) {
            val chapters = getChapters(tables.last())
            return ReadingPage(
                fullManga.id, fullManga.title, fullManga.synopsis, fullManga.published.from, fullManga.status,
                fullManga.authors.first().name, fullManga.genres.map { genre: Genre -> genre.name }, chapters,
                isFavorite
            )
        }
        return null
    }

    private fun getDocument(manga: FullManga?): Document {
        manga?.let {
            return try {
                // We can have issues with the name used on MAL compare to MangaReader or MangaPanda
                // First, we try with the default title
                getManga(manga.getWebTitle())
            } catch (exception: HttpStatusException) {
                try {
                    // Then, with the english title, can be useful for Manga like "Haikyuu' on MAL
                    // Instead of Haikyu on MangaReader
                    getManga(manga.getWebTitle(manga.englishTitle))
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
            chapters.add(LinkChapter(ahref.text(), addedDate, buildLink(link)))
        }
        return chapters
    }

    override suspend fun fromLinkChapterToChapter(chapterLink: String): Chapter? {
        try {
            val document: Document = getDocument(chapterLink)
            val scripts: Elements = document.select("script")
            if (scripts.size > 1) {
                val jsonContent: String = scripts[1].data().replace("document[\"mj\"]=", "")
                convertJsonToChapter(jsonContent)?.let { chapter: Chapter ->
                    for (image in chapter.images) {
                        image.link = "https://${image.link}"
                    }
                    return chapter
                }
            }
        } catch(e: Exception) {
            println("Exception for ${chapterLink}: ${e.message}")
        }
        return null
    }
}