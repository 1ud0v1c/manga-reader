package com.ludovic.vimont.mangareader.screens.detail

import com.ludovic.vimont.mangareader.entities.ReadingPage

interface DetailRepository {
    suspend fun launchDownload(mangaTitle: String): ReadingPage
}