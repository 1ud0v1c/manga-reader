package com.ludovic.vimont.mangareader.screens.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ludovic.vimont.mangareader.entities.LinkChapter
import com.ludovic.vimont.mangareader.entities.ReadingPage
import com.ludovic.vimont.mangareader.entities.SortOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DetailViewModel(
    private val detailRepository: DetailRepository
): ViewModel() {
    private val _sortOrder = MutableLiveData(SortOrder.OLD_TO_RECENT)
    val sortOrder: LiveData<SortOrder> = _sortOrder

    private val _mangaName = MutableLiveData<String>()
    val mangaName: LiveData<String> = _mangaName

    private val _readingPage = MutableLiveData<ReadingPage>()
    val readingPage: LiveData<ReadingPage> = _readingPage

    fun fetchMangaName(mangaId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val loadedTitle: String = detailRepository.fetchMangaName(mangaId)
            _mangaName.postValue(loadedTitle)
        }
    }

    fun fetchMangaContent(mangaId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val loadedPage: ReadingPage = detailRepository.fetchMangaContent(mangaId)
            _readingPage.postValue(loadedPage.getSortedChapters(sortOrder.value))
        }
    }

    private fun ReadingPage.getSortedChapters(sortOrder: SortOrder?): ReadingPage {
        val sortedChapters = when (sortOrder) {
            SortOrder.OLD_TO_RECENT -> chapters.sortedWith(compareByDescending { it.index })
            SortOrder.RECENT_TO_OLD -> chapters.sortedWith(compareBy { it.index })
            else -> throw IllegalArgumentException("We should have a default sorting value")
        }
        return copy(chapters = sortedChapters)
    }

    fun downloadChapters(chapters: List<LinkChapter>) {
        viewModelScope.launch(Dispatchers.Default) {
            detailRepository.downloadChapters(chapters)
        }
    }

    fun addToFavorite(mangaId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            detailRepository.addToFavorite(mangaId)
        }
    }

    fun removeFromFavorite(mangaId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            detailRepository.removeFromFavorite(mangaId)
        }
    }

    fun onSortClicked() {
        val actualSortOrder = sortOrder.value ?: return
        val newSortOrder = when (actualSortOrder) {
            SortOrder.OLD_TO_RECENT -> SortOrder.RECENT_TO_OLD
            SortOrder.RECENT_TO_OLD -> SortOrder.OLD_TO_RECENT
        }
        _sortOrder.value = newSortOrder
    }
}