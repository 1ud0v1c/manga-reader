package com.ludovic.vimont.mangareader.screens.detail

import com.ludovic.vimont.mangareader.entities.ReadingPage

interface DetailRepository {
    suspend fun launchDownload(mangaId: String): ReadingPage
}