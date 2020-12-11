package com.ludovic.vimont.mangareader.di

import com.ludovic.vimont.mangareader.screens.list.ListRepositoryImpl
import com.ludovic.vimont.mangareader.screens.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ViewModelModule {
    val viewModelModule: Module = module {
        viewModel {
            ListViewModel(get<ListRepositoryImpl>())
        }
    }
}