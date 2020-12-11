package com.ludovic.vimont.mangareader

import android.app.Application
import com.ludovic.vimont.mangareader.di.DataSourceModule
import com.ludovic.vimont.mangareader.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

@Suppress("unused")
class MangaReaderApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MangaReaderApp)
            val listOfModule: List<Module> = listOf(
                DataSourceModule.repositoriesModule,
                ViewModelModule.viewModelModule
            )
            modules(listOfModule)
        }
    }
}