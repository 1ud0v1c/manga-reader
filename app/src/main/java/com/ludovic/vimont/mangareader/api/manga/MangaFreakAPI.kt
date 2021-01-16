package com.ludovic.vimont.mangareader.api.manga

import com.ludovic.vimont.mangareader.api.MangaAPI
import com.ludovic.vimont.mangareader.db.MangaDao
import com.ludovic.vimont.mangareader.entities.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.*
import kotlin.collections.ArrayList

class MangaFreakAPI(private val mangaDao: MangaDao): MangaAPI {
    companion object {
        private const val BASE_URL = "https://w11.mangafreak.net"

        fun searchManga(mangaTitle: String): Document {
            return getDocument("$BASE_URL/Search/$mangaTitle")
        }

        fun getDocument(url: String): Document {
            return Jsoup.connect(url).get()
        }

        fun buildLink(path: String): String {
            return BASE_URL + path
        }
    }

    override suspend fun fromMangaToDetailPage(fullManga: FullManga): ReadingPage? {
        val document: Document = searchManga(fullManga.getWebTitle())
        val results: Elements = document.select(".manga_search_item")
        if (results.isNotEmpty()) {
            val searchResult = results[0]
            val mangaLink = buildLink(searchResult.select("a").first().attr("href"))
            val chaptersDocument = getDocument(mangaLink)
            val contentChapter = chaptersDocument.select(".manga_series_list").first()
            val chaptersLinks = contentChapter.select("tbody").first().select("tr")
            val chapters = ArrayList<LinkChapter>()
            for (chapterLink in chaptersLinks) {
                val tds: Elements = chapterLink.select("td")
                val link = tds.first()
                val addedDate: String = tds[1].text()
                chapters.add(LinkChapter(link.text(), addedDate, link.attr("href")))
            }
            val isFavorite = mangaDao.get(fullManga.id).isFavorite
            return ReadingPage(
                fullManga.id, fullManga.title, fullManga.synopsis, fullManga.published.from, fullManga.status,
                fullManga.authors.first().name, fullManga.genres.map { genre: Genre -> genre.name },
                chapters.reversed(), isFavorite
            )
        }
        return null
    }

    override suspend fun fromLinkToChapter(chapterLink: String): Chapter? {
        try {
            val document: Document = getDocument(chapterLink)
            val title = document.select("h1.title").first()
            val name = title.select("a").text().toLowerCase(Locale.getDefault()).capitalize(Locale.getDefault())
            val currentChapter = chapterLink.split("/").last().split("_").last().toInt()
            val imageContainer = document.select(".slideshow-container").first()
            val images: Elements = imageContainer.select("img")
            val chapterPages = ArrayList<ChapterPage>()
            for (image in images) {
                val imageSrc = image.attr("src")
                chapterPages.add(ChapterPage(images.indexOf(image), imageSrc))
            }
            return Chapter(currentChapter, chapterPages, name,"", "", "")
        } catch(e: Exception) {
            println("Exception for ${chapterLink}: ${e.message}")
        }
        return null
    }
}