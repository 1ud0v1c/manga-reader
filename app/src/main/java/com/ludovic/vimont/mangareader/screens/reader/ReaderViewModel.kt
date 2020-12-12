package com.ludovic.vimont.mangareader.screens.reader

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ludovic.vimont.mangareader.entities.Chapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReaderViewModel(private val readerRepository: ReaderRepository): ViewModel() {
    val chapter = MutableLiveData<Chapter>()

    fun loadChapter(chapterLink: String) {
        viewModelScope.launch(Dispatchers.Default) {
            readerRepository.loadChapter(chapterLink)?.let {
                chapter.postValue(it)
            }
        }
    }
}