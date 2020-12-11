package com.ludovic.vimont.mangareader.screens.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ludovic.vimont.mangareader.entities.Chapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepository: DetailRepository): ViewModel() {
    val chapters = MutableLiveData<List<Chapter>>()

    fun downloadContent(mangaTitle: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val loadedChapters: List<Chapter> = detailRepository.launchDownload(mangaTitle)
            chapters.postValue(loadedChapters)
        }
    }
}