package com.ludovic.vimont.mangareader.screens.favorite

import com.ludovic.vimont.mangareader.entities.Manga

interface FavoriteRepository {
    suspend fun loadFavorites(): List<Manga>
}