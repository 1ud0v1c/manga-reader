package com.ludovic.vimont.mangareader.entities

data class LinkChapter(val title: String,
                       val addedDate: String,
                       val link: String) {
    fun getCurrentChapter(): String {
        val matchResult: MatchResult? = Regex("(Chapter\\s[0-9.]+)").find(title)
        matchResult?.let {
            val (name) = it.destructured
            return name
        }
        return title
    }
}