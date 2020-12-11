package com.ludovic.vimont.mangareader.screens.detail

import com.ludovic.vimont.mangareader.api.MangaReaderAPI
import com.ludovic.vimont.mangareader.entities.Chapter
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class DetailRepositoryImpl: DetailRepository {
    override suspend fun launchDownload(mangaTitle: String): List<Chapter> {
        val chapters = ArrayList<Chapter>()
        val document: Document = MangaReaderAPI.getManga(mangaTitle)
        val table: Element = document.select("table")[1]
        val trs: Elements = table.select("tr")
        trs.removeFirst()
        for (tr: Element in trs) {
            val tds: Elements = tr.select("td")
            val ahref: Element = tds[0].selectFirst("a")
            val addedDate: String = tds[1].text()
            chapters.add(Chapter(ahref.text(), addedDate, MangaReaderAPI.buildLink(ahref.attr("href"))))
        }
        return chapters
    }
}