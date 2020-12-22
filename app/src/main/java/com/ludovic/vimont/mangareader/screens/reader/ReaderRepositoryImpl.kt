package com.ludovic.vimont.mangareader.screens.reader

import com.ludovic.vimont.mangareader.api.MangaAPI
import com.ludovic.vimont.mangareader.entities.Chapter

class ReaderRepositoryImpl(private val mangaAPI: MangaAPI): ReaderRepository {
    override suspend fun loadChapter(chapterLink: String): Chapter? {
        return mangaAPI.fromLinkChapterToChapter(chapterLink)
    }
}