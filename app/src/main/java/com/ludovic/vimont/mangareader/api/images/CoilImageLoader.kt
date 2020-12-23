package com.ludovic.vimont.mangareader.api.images

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.request.SuccessResult
import com.ludovic.vimont.mangareader.api.ImageLoader

class CoilImageLoader(private val imageLoader: coil.ImageLoader,
                      private val imageRequestBuilder: ImageRequest.Builder): ImageLoader {
    override suspend fun downloadBitmap(url: String): Bitmap {
        val request = imageRequestBuilder.data(url)
            .allowHardware(false)
            .build()
        val imageResult: ImageResult = imageLoader.execute(request)
        val result = (imageResult as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
}