package com.ludovic.vimont.mangareader.di

import coil.ImageLoader
import coil.request.ImageRequest
import com.ludovic.vimont.mangareader.api.*
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
            ImageLoader(androidContext())
        }
        single {
            ImageRequest.Builder(androidContext())
        }
        single {
            MangaReaderAPI(get())
        }
        single {
            MangakakalotAPI(get())
        }
        factory {
            FileDownloader(get(), get(), get())
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
            DetailRepositoryImpl(get(), get<MangakakalotAPI>(), get(), get())
        }
        factory {
            ReaderRepositoryImpl(get<MangakakalotAPI>())
        }
        factory {
            FavoriteRepositoryImpl(get())
        }
    }
}