package com.ludovic.vimont.mangareader.screens.detail

import com.ludovic.vimont.mangareader.entities.LinkChapter
import com.ludovic.vimont.mangareader.entities.ReadingPage

interface DetailRepository {
    suspend fun fetchMangaContent(mangaId: String): ReadingPage

    suspend fun downloadChapters(chapters: List<LinkChapter>)
}