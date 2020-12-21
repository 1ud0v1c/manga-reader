package com.ludovic.vimont.mangareader.db

import android.os.Build
import com.ludovic.vimont.mangareader.MockModel
import com.ludovic.vimont.mangareader.entities.Manga
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*
import kotlin.collections.ArrayList

@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class MangaDaoTest: AutoCloseKoinTest() {
    private val mangas = ArrayList<Manga>()
    private val mangasDao: MangaDao by inject()

    @Before
    fun setUp() {
        mangas.add(MockModel.buildManga())
        mangas.add(MockModel.buildManga())
        mangas.add(MockModel.buildManga())
        mangas.add(MockModel.buildManga())
    }

    @Test
    fun testCount() = runBlocking {
        Assert.assertEquals(0, mangasDao.count())
        mangasDao.insert(mangas)
        Assert.assertEquals(mangas.size, mangasDao.count())
    }

    @Test
    fun testInsert() = runBlocking {
        Assert.assertEquals(0, mangasDao.getAll().size)
        mangasDao.insert(mangas)
        Assert.assertEquals(mangas.size, mangasDao.getAll().size)
    }

    @Test
    fun testGet(): Unit = runBlocking {
        val noManga = mangasDao.get(UUID.randomUUID().toString())
        Assert.assertNull(noManga)
        mangasDao.insert(mangas)
        Assert.assertEquals(mangas[0], mangasDao.get(mangas[0].id))
    }

    @Test
    fun testGetFavorites() = runBlocking {
        mangasDao.insert(mangas)
        var countFavorite = 0
        for (manga in mangas) {
            if (manga.isFavorite) {
                countFavorite += 1
            }
        }
        Assert.assertEquals(countFavorite, mangasDao.getFavorites().size)
    }

    @Test
    fun testUpdateFavorite() = runBlocking {
        for (manga in mangas) {
            manga.isFavorite = false
        }
        mangasDao.insert(mangas)

        val manga = mangas[0]
        Assert.assertEquals(false, manga.isFavorite)

        mangasDao.updateFavorite(manga.id, true)
        var dbManga = mangasDao.get(manga.id)
        Assert.assertEquals(true, dbManga.isFavorite)

        mangasDao.updateFavorite(manga.id, false)
        dbManga = mangasDao.get(manga.id)
        Assert.assertEquals(false, dbManga.isFavorite)
    }

    @Test
    fun testDrop() = runBlocking {
        mangasDao.insert(mangas)
        Assert.assertTrue(mangasDao.getAll().isNotEmpty())
        mangasDao.drop()
        Assert.assertTrue(mangasDao.getAll().isEmpty())
    }
}