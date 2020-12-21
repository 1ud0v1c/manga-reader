package com.ludovic.vimont.mangareader

import com.ludovic.vimont.mangareader.entities.Manga
import kotlin.random.Random

object MockModel {
    private const val RANDOM_IMAGE_API = "https://picsum.photos"

    fun buildManga(isFavorite: Boolean = Random.nextBoolean()): Manga {
        val id: String = Random.nextInt(1, 1_000_000).toString()
        val title: String = RandomGenerator.alphanumericalString()
        val url = "https://www.${RandomGenerator.alphanumericalString()}"
        val startDate: String = RandomGenerator.date()
        val endDate: String? = if (Random.nextBoolean()) RandomGenerator.date() else null
        val score: Float = Random.nextDouble(0.0, 10.0).toFloat()
        val volumes: Int = Random.nextInt(20, 300)
        val cover = "$RANDOM_IMAGE_API/${Random.nextInt(100, 500)}"
        return Manga(id, title, url, startDate, endDate, score, volumes, cover, isFavorite)
    }
}