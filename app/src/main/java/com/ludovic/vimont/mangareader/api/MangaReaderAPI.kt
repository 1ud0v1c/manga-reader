package com.ludovic.vimont.mangareader.api

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * https://www.mangareader.net/
 */
interface MangaReaderAPI {
    companion object {
        const val BASE_URL = "https://www.mangareader.net/"

        const val NAME_INDEX = 0
        const val YEAR_OF_RELEASE_INDEX = 2
        const val STATUS_INDEX = 3
        const val AUTHOR_INDEX = 4

        fun getManga(mangaTitle: String): Document {
            return Jsoup.connect(BASE_URL + mangaTitle).get()
        }

        fun buildLink(path: String): String {
            return BASE_URL + path
        }
    }
}