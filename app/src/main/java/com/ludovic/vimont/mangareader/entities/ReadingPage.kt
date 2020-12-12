package com.ludovic.vimont.mangareader.entities

data class ReadingPage(val name: String,
                       val synopsis: String,
                       val yearOfRelease: Int,
                       val status: String,
                       val author: String,
                       val genres: List<String>,
                       val chapters: List<Chapter>)