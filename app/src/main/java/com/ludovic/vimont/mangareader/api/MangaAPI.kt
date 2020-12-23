package com.ludovic.vimont.mangareader.api

import com.ludovic.vimont.mangareader.entities.Chapter
import com.ludovic.vimont.mangareader.entities.FullManga
import com.ludovic.vimont.mangareader.entities.ReadingPage

interface MangaAPI {
    companion object {
        val headers = mapOf(
            "Referer" to "https://manganelo.com/chapter/kxqh9261558062112/chapter_1",
            "User-Agent" to "Mozilla/5.0 (X11; Linux x86_64; rv:83.0) Gecko/20100101 Firefox/83.0"
        )
    }

    suspend fun fromMangaToDetailPage(fullManga: FullManga): ReadingPage?

    suspend fun fromLinkToChapter(chapterLink: String): Chapter?
}