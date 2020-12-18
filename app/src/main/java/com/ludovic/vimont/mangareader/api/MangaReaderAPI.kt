package com.ludovic.vimont.mangareader.api

import com.ludovic.vimont.mangareader.entities.Chapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * https://www.mangareader.net/
 */
interface MangaReaderAPI {
    companion object {
        private const val BASE_URL = "https://www.mangareader.net/"

        fun getManga(mangaTitle: String): Document {
            println("$mangaTitle")
            return getDocument(BASE_URL + mangaTitle)
        }

        fun getDocument(url: String): Document {
            return Jsoup.connect(url).get()
        }

        fun buildLink(path: String): String {
            return BASE_URL + path
        }

        fun convertJsonToChapter(jsonContent: String): Chapter? {
            val moshi = Moshi.Builder().build()
            val type = Types.newParameterizedType(Chapter::class.java)
            val adapter = moshi.adapter<Chapter>(type)
            return adapter.fromJson(jsonContent)
        }
    }
}