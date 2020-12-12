package com.ludovic.vimont.mangareader.screens.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ludovic.vimont.mangareader.entities.Chapter
import com.ludovic.vimont.mangareader.entities.ReadingPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepository: DetailRepository): ViewModel() {
    val readingPage = MutableLiveData<ReadingPage>()

    fun downloadContent(mangaTitle: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val loadedPage: ReadingPage = detailRepository.launchDownload(mangaTitle)
            readingPage.postValue(loadedPage)
        }
    }
}