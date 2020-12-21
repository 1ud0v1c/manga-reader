package com.ludovic.vimont.mangareader.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ludovic.vimont.mangareader.entities.Manga

/**
 * Handle the creation of the database by extending RoomDatabase.
 */
@Database(entities = [Manga::class], version = 1)
abstract class MangaReaderDatabase: RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "manga_reader_database"

        fun buildDatabase(context: Context): MangaReaderDatabase {
            return Room.databaseBuilder(context, MangaReaderDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun mangaDao(): MangaDao
}