package com.ludovic.vimont.mangareader.screens.reader

import com.ludovic.vimont.mangareader.api.MangaReaderAPI
import com.ludovic.vimont.mangareader.entities.Chapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class ReaderRepositoryImpl: ReaderRepository {
    override suspend fun loadChapter(chapterLink: String): Chapter? {
        val document: Document = MangaReaderAPI.getDocument(chapterLink)
        val scripts: Elements = document.select("script")
        if (scripts.size > 1) {
            val jsonContent: String = scripts[1].data().replace("document[\"mj\"]=", "")
            convertJsonToChapter(jsonContent)?.let { chapter: Chapter ->
                return chapter
            }
        }
        return null
    }

    private fun convertJsonToChapter(jsonContent: String): Chapter? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(Chapter::class.java)
        val adapter = moshi.adapter<Chapter>(type)
        return adapter.fromJson(jsonContent)
    }
}