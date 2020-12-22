package com.ludovic.vimont.mangareader.screens.reader

import com.ludovic.vimont.mangareader.api.MangaAPI
import com.ludovic.vimont.mangareader.api.MangaReaderAPI
import com.ludovic.vimont.mangareader.entities.Chapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class ReaderRepositoryImpl(private val mangaAPI: MangaAPI): ReaderRepository {
    override suspend fun loadChapter(chapterLink: String): Chapter? {
        return mangaAPI.fromLinkChapterToChapter(chapterLink)
    }
}