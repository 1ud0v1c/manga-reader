package com.ludovic.vimont.mangareader.screens.detail

import com.ludovic.vimont.mangareader.api.MangaReaderAPI
import com.ludovic.vimont.mangareader.entities.LinkChapter
import com.ludovic.vimont.mangareader.entities.ReadingPage
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class DetailRepositoryImpl: DetailRepository {
    override suspend fun launchDownload(mangaTitle: String): ReadingPage {
        val document: Document = MangaReaderAPI.getManga(mangaTitle)
        val tables: Elements = document.select("table")

        val infoTrs: Elements = tables.first().select("tr")
        val name = extractField(infoTrs, MangaReaderAPI.NAME_INDEX)
        val yearOfRelease = extractField(infoTrs, MangaReaderAPI.YEAR_OF_RELEASE_INDEX).toInt()
        val status = extractField(infoTrs, MangaReaderAPI.STATUS_INDEX)
        val author = extractField(infoTrs, MangaReaderAPI.AUTHOR_INDEX)
        val genres = getGenres(document)
        val synopsisElement: Element = document.select("div.d46").first()
        val synopsis = synopsisElement.select("p").first().text()
        val chapters = getChapters(tables.last())

        return ReadingPage(name, synopsis, yearOfRelease, status, author, genres, chapters)
    }

    private fun extractField(infoTrs: Elements, index: Int = 0): String {
        return infoTrs[index].select("td").last().text()
    }

    private fun getGenres(document: Document): List<String> {
        val genres = ArrayList<String>()
        val links: Elements = document.select("a.d42")
        for (link in links) {
            genres.add(link.text())
        }
        return genres
    }

    private fun getChapters(table: Element): ArrayList<LinkChapter> {
        val chapters = ArrayList<LinkChapter>()
        val trs: Elements = table.select("tr")
        trs.removeFirst()
        for (tr: Element in trs) {
            val tds: Elements = tr.select("td")
            val ahref: Element = tds[0].selectFirst("a")
            val addedDate: String = tds[1].text()
            chapters.add(
                LinkChapter(
                    ahref.text(),
                    addedDate,
                    MangaReaderAPI.buildLink(ahref.attr("href"))
                )
            )
        }
        return chapters
    }
}