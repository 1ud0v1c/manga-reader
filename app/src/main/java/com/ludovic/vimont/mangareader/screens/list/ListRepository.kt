package com.ludovic.vimont.mangareader.screens.list

import com.ludovic.vimont.mangareader.entities.Manga

interface ListRepository {
    suspend fun list(page: Int): List<Manga>
}