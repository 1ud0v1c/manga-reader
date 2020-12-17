package com.ludovic.vimont.mangareader.screens.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ludovic.vimont.mangareader.entities.LinkChapter
import com.ludovic.vimont.mangareader.entities.ReadingPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepository: DetailRepository): ViewModel() {
    val readingPage = MutableLiveData<ReadingPage>()

    fun fetchMangaContent(mangaId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val loadedPage: ReadingPage = detailRepository.fetchMangaContent(mangaId)
            readingPage.postValue(loadedPage)
        }
    }

    fun downloadChapters(chapters: List<LinkChapter>) {
        viewModelScope.launch(Dispatchers.Default) {
            detailRepository.downloadChapters(chapters)
        }
    }
}