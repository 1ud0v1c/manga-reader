package com.ludovic.vimont.mangareader.api

import android.os.Build
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class JikanAPITest: KoinTest {
    private val jikanAPI: JikanAPI by inject()

    @After
    fun tearDown() {
        GlobalContext.stopKoin()
    }

    @Test
    fun testGetPopularMangas(): Unit = runBlocking {
        jikanAPI.getPopularMangas().body()?.let {
            Assert.assertTrue(it.top.isNotEmpty())
        }
    }
}