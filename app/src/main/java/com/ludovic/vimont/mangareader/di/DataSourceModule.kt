package com.ludovic.vimont.mangareader.di

import com.bumptech.glide.Glide
import com.ludovic.vimont.mangareader.api.JikanAPI
import com.ludovic.vimont.mangareader.api.MangaReaderAPI
import com.ludovic.vimont.mangareader.api.RetrofitBuilder
import com.ludovic.vimont.mangareader.db.MangaReaderDatabase
import com.ludovic.vimont.mangareader.screens.detail.DetailRepositoryImpl
import com.ludovic.vimont.mangareader.screens.favorite.FavoriteRepositoryImpl
import com.ludovic.vimont.mangareader.screens.list.ListRepositoryImpl
import com.ludovic.vimont.mangareader.screens.reader.ReaderRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object DataSourceModule {
    val repositoriesModule: Module = module {
        buildAndroidEntities()
        buildNetworkEntities()
        buildDatabase()
        buildRepositoriesEntities()
    }

    private fun Module.buildAndroidEntities() {
        single {
            androidContext().contentResolver
        }
    }

    private fun Module.buildNetworkEntities() {
        single {
            RetrofitBuilder.buildRetrofitForAPI(JikanAPI.BASE_URL, JikanAPI::class.java)
        }
        single {
            Glide.with(androidContext())
        }
    }

    private fun Module.buildDatabase() {
        single {
            MangaReaderDatabase.buildDatabase(androidContext())
        }
        single {
            get<MangaReaderDatabase>().mangaDao()
        }
    }

    private fun Module.buildRepositoriesEntities() {
        factory {
            ListRepositoryImpl(get(), get())
        }
        factory {
            DetailRepositoryImpl(get(), get(), get(), get())
        }
        factory {
            ReaderRepositoryImpl()
        }
        factory {
            FavoriteRepositoryImpl(get())
        }
    }
}