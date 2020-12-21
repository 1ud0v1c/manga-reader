package com.ludovic.vimont.mangareader

import java.util.*
import kotlin.random.Random

object RandomGenerator {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun alphanumericalString(length: Int = 12): String {
        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    fun date(): String {
        val day = Random.nextInt(1, 31)
        val month = Random.nextInt(1, 12)
        val year = Random.nextInt(1970, Calendar.getInstance().get(Calendar.YEAR))
        return "$day/$month/$year"
    }
}