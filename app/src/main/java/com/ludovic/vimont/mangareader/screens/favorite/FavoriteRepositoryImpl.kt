package com.ludovic.vimont.mangareader.screens.favorite

import com.ludovic.vimont.mangareader.db.MangaDao
import com.ludovic.vimont.mangareader.entities.Manga

class FavoriteRepositoryImpl(private val mangaDao: MangaDao): FavoriteRepository {
    override suspend fun loadFavorites(): List<Manga> {
        return mangaDao.getFavorites()
    }
}