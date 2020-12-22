package com.ludovic.vimont.mangareader.api.images

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.ludovic.vimont.mangareader.api.ImageLoader
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class FileDownloader(private val imageLoader: ImageLoader,
                     private val contentResolver: ContentResolver) {
    suspend fun downloadBitmap(url: String): Bitmap {
        return imageLoader.downloadBitmap(url)
    }

    fun saveImage(bitmap: Bitmap, folder: String, name: String) {
        val fileOutputStream: OutputStream? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/$folder/")
            val imageUri: Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            imageUri?.let {
                contentResolver.openOutputStream(imageUri)
            }
        } else {
            val pictureDirectory = Environment.DIRECTORY_PICTURES
            val imagesDir: String = Environment.getExternalStoragePublicDirectory(pictureDirectory).toString()
            val image = File(imagesDir, "$name.jpg")
            FileOutputStream(image)
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream?.close()
    }
}