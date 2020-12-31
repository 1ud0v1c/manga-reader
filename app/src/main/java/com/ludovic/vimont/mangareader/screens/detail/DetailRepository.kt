package com.ludovic.vimont.mangareader.screens.detail

import com.ludovic.vimont.mangareader.entities.LinkChapter
import com.ludovic.vimont.mangareader.entities.Manga
import com.ludovic.vimont.mangareader.entities.ReadingPage

interface DetailRepository {
    suspend fun fetchMangaName(mangaId: String): String

    suspend fun fetchMangaContent(mangaId: String): ReadingPage

    suspend fun downloadChapters(chapters: List<LinkChapter>)

    suspend fun addToFavorite(mangaId: String)

    suspend fun removeFromFavorite(mangaId: String)
}