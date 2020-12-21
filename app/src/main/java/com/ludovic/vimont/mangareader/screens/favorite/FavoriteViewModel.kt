package com.ludovic.vimont.mangareader.screens.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ludovic.vimont.mangareader.entities.Manga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository): ViewModel() {
    val mangas = MutableLiveData<List<Manga>>()

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.Default) {
            val loadedMangas = favoriteRepository.loadFavorites()
            mangas.postValue(loadedMangas)
        }
    }
}