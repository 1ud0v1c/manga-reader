package com.ludovic.vimont.mangareader.di

import com.ludovic.vimont.mangareader.screens.detail.DetailRepositoryImpl
import com.ludovic.vimont.mangareader.screens.detail.DetailViewModel
import com.ludovic.vimont.mangareader.screens.list.ListRepositoryImpl
import com.ludovic.vimont.mangareader.screens.list.ListViewModel
import com.ludovic.vimont.mangareader.screens.reader.ReaderRepositoryImpl
import com.ludovic.vimont.mangareader.screens.reader.ReaderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ViewModelModule {
    val viewModelModule: Module = module {
        viewModel {
            ListViewModel(get<ListRepositoryImpl>())
        }
        viewModel {
            DetailViewModel(get<DetailRepositoryImpl>())
        }
        viewModel {
            ReaderViewModel(get<ReaderRepositoryImpl>())
        }
    }
}