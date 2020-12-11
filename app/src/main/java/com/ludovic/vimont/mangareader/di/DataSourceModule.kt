package com.ludovic.vimont.mangareader.di

import com.ludovic.vimont.mangareader.api.JikanAPI
import com.ludovic.vimont.mangareader.api.MangaReaderAPI
import com.ludovic.vimont.mangareader.api.RetrofitBuilder
import com.ludovic.vimont.mangareader.screens.detail.DetailRepositoryImpl
import com.ludovic.vimont.mangareader.screens.list.ListRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

object DataSourceModule {
    val repositoriesModule: Module = module {
        buildNetworkEntities()
        buildRepositoriesEntities()
    }

    private fun Module.buildNetworkEntities() {
        single {
            RetrofitBuilder.buildRetrofitForAPI(JikanAPI.BASE_URL, JikanAPI::class.java)
        }
    }

    private fun Module.buildRepositoriesEntities() {
        factory {
            ListRepositoryImpl(get())
        }
        factory {
            DetailRepositoryImpl()
        }
    }
}