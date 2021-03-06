package com.ludovic.vimont.mangareader.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ludovic.vimont.mangareader.entities.Manga

@Dao
interface MangaDao {
    @Query("SELECT count(id) FROM manga")
    suspend fun count(): Int

    @Query("SELECT * FROM manga")
    suspend fun getAll(): List<Manga>

    @Query("SELECT * FROM manga WHERE isFavorite=1")
    suspend fun getFavorites(): List<Manga>

    @Query("SELECT * FROM manga WHERE id=:id")
    suspend fun get(id: String): Manga

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mangas: List<Manga>)

    @Query("UPDATE manga SET isFavorite=:isFavorite WHERE id=:id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

    @Query("DELETE FROM manga")
    suspend fun drop()
}