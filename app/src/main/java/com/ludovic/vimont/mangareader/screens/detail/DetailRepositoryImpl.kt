package com.ludovic.vimont.mangareader.screens.detail

import com.ludovic.vimont.mangareader.api.JikanAPI
import com.ludovic.vimont.mangareader.api.MangaReaderAPI
import com.ludovic.vimont.mangareader.entities.FullManga
import com.ludovic.vimont.mangareader.entities.LinkChapter
import com.ludovic.vimont.mangareader.entities.ReadingPage
import org.jsoup.HttpStatusException
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import retrofit2.Response

class DetailRepositoryImpl(private val jikanAPI: JikanAPI): DetailRepository {
    override suspend fun launchDownload(mangaId: String): ReadingPage {
        var manga: FullManga? = null
        val response: Response<FullManga> = jikanAPI.getManga(mangaId)
        if (response.isSuccessful) {
            response.body()?.let { fetchManga: FullManga ->
                manga = fetchManga
            }
        }

        val document: Document = getDocument(manga)
        val tables: Elements = document.select("table")

        val infoTrs: Elements = tables.first().select("tr")
        val name = extractField(infoTrs, MangaReaderAPI.NAME_INDEX)
        val yearOfRelease = extractField(infoTrs, MangaReaderAPI.YEAR_OF_RELEASE_INDEX)
        val status = extractField(infoTrs, MangaReaderAPI.STATUS_INDEX)
        val author = extractField(infoTrs, MangaReaderAPI.AUTHOR_INDEX)
        val genres = getGenres(document)
        val synopsisElement: Element = document.select("div.d46").first()
        val synopsis = synopsisElement.select("p").first().text()
        val chapters = getChapters(tables.last())

        return ReadingPage(name, synopsis, yearOfRelease, status, author, genres, chapters)
    }

    private fun getDocument(manga: FullManga?): Document {
        manga?.let {
            return try {
                // We can have issues with the name used on MAL compare to MangaReader or MangaPanda
                // First, we try with the default title
                MangaReaderAPI.getManga(manga.getWebTitle())
            } catch(exception: HttpStatusException) {
                try {
                    // Then, with the english title, can be useful for Manga like "Haikyuu' on MAL
                    // Instead of Haikyu on MangaReader
                    MangaReaderAPI.getManga(manga.getWebTitle(manga.englishTitle))
                } catch(exception: HttpStatusException) {
                    Document.createShell("")
                }
            }
        }
        return Document.createShell("")
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