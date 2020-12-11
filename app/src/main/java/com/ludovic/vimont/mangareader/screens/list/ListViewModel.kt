package com.ludovic.vimont.mangareader.screens.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ludovic.vimont.mangareader.entities.Manga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(private val listRepository: ListRepository): ViewModel() {
    val mangas = MutableLiveData<List<Manga>>()

    fun fetchMangas() {
        viewModelScope.launch(Dispatchers.Default) {
            val loadedMangas = listRepository.list()
            mangas.postValue(loadedMangas)
        }
    }
}