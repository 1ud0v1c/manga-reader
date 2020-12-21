package com.ludovic.vimont.mangareader.entities

data class ReadingPage(val id: String,
                       val name: String,
                       val synopsis: String,
                       val yearOfRelease: String,
                       val status: String,
                       val author: String,
                       val genres: List<String>,
                       val chapters: List<LinkChapter>,
                       val isFavorite: Boolean = false)