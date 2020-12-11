package com.ludovic.vimont.mangareader.screens.detail

import com.ludovic.vimont.mangareader.entities.Chapter

interface DetailRepository {
    suspend fun launchDownload(mangaTitle: String): List<Chapter>
}