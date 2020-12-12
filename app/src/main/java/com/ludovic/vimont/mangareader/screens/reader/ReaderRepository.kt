package com.ludovic.vimont.mangareader.screens.reader

import com.ludovic.vimont.mangareader.entities.Chapter

interface ReaderRepository {
    suspend fun loadChapter(chapterLink: String): Chapter?
}