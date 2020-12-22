package com.ludovic.vimont.mangareader.api

import com.ludovic.vimont.mangareader.entities.Chapter
import com.ludovic.vimont.mangareader.entities.FullManga
import com.ludovic.vimont.mangareader.entities.ReadingPage

interface MangaAPI {
    suspend fun fromMangaToReadingPage(fullManga: FullManga): ReadingPage?

    suspend fun fromLinkChapterToChapter(chapterLink: String): Chapter?
}