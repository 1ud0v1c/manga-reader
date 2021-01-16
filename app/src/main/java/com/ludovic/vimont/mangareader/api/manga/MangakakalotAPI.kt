package com.ludovic.vimont.mangareader.api.manga

import com.ludovic.vimont.mangareader.api.MangaAPI
import com.ludovic.vimont.mangareader.db.MangaDao
import com.ludovic.vimont.mangareader.entities.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.util.*
import kotlin.collections.ArrayList

class MangakakalotAPI(private val mangaDao: MangaDao): MangaAPI {
    companion object {
        private const val BASE_URL = "https://mangakakalot.com"

        fun searchManga(mangaTitle: String): Document {
            return getDocument("$BASE_URL/search/story/$mangaTitle")
        }

        fun getDocument(url: String): Document {
            return Jsoup.connect(url).get()
        }
    }

    override suspend fun fromMangaToDetailPage(fullManga: FullManga): ReadingPage? {
        val document: Document = searchManga(fullManga.getWebTitle())
        val results: Elements = document.select(".story_item")
        if (results.isNotEmpty()) {
            val searchResult = results[0]
            val mangaLink = searchResult.select("a").first().attr("href")
            val chaptersDocument = getDocument(mangaLink)
            val contentChapter = chaptersDocument.select(".row-content-chapter").first()
            val chaptersLinks = contentChapter.select(".a-h")
            val chapters = ArrayList<LinkChapter>()
            for (chapterLink in chaptersLinks) {
                val ahref: Element = chapterLink.select("a").first()
                val link = ahref.attr("href")
                val addedDate: String = chapterLink.select(".chapter-time").first().text()
                chapters.add(LinkChapter(ahref.text(), addedDate, link))
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
            val document: Document = MangaReaderAPI.getDocument(chapterLink)
            val breadcrumb = document.select(".panel-breadcrumb").first()
            val breadcrumbLinks = breadcrumb.select("a")
            val name = if (breadcrumbLinks.isNotEmpty()) breadcrumbLinks[1].text() else ""
            val currentChapter = chapterLink.split("/").last().split("_").last()
            val container = document.select(".container-chapter-reader").first()
            val images: Elements = container.select("img")
            val chapterPages = ArrayList<ChapterPage>()
            for (image in images) {
                val imageSrc = image.attr("src")
                chapterPages.add(ChapterPage(images.indexOf(image), imageSrc))
            }
            val linksContainer = document.select(".navi-change-chapter-btn").first()
            val links = linksContainer.select("a")
            var previous = ""
            var next = ""
            for (link in links) {
                if (link.text().toLowerCase(Locale.getDefault()).contains("prev")) {
                    previous = link.attr("href")
                }
                if (link.text().toLowerCase(Locale.getDefault()).contains("next")) {
                    next = link.attr("href")
                }
            }
            return Chapter(currentChapter, chapterPages, name, previous, next, "")
        } catch(e: Exception) {
            println("Exception for ${chapterLink}: ${e.message}")
        }
        return null
    }
}