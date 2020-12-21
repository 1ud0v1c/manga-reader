package com.ludovic.vimont.mangareader.api

import android.os.Build
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class JikanAPITest: AutoCloseKoinTest() {
    private val jikanAPI: JikanAPI by inject()

    @Test
    fun testGetPopularMangas(): Unit = runBlocking {
        jikanAPI.getPopularMangas().body()?.let {
            Assert.assertTrue(it.top.isNotEmpty())
        }
    }
}