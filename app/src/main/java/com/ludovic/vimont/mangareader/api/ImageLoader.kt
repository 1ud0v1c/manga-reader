package com.ludovic.vimont.mangareader.api

import android.graphics.Bitmap

interface ImageLoader {
    suspend fun downloadBitmap(url: String): Bitmap
}