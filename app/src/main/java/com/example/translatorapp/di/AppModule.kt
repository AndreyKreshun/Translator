package com.example.translatorapp.di

import androidx.room.Room
import com.example.translatorapp.data.local.TranslationDatabase
import com.example.translatorapp.data.repository.TranslationRepository
import com.example.translatorapp.presentation.viewmodels.FavoritesViewModel
import com.example.translatorapp.presentation.viewmodels.HistoryViewModel
import com.example.translatorapp.presentation.viewmodels.TranslationViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Retrofit
import com.example.translatorapp.data.network.SkyengApiService

val appModule = module {
    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            TranslationDatabase::class.java,
            "translation_db"
        ).build()
    }

    // DAO
    single { get<TranslationDatabase>().historyDao() }

    // API Service
    single {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        Retrofit.Builder()
            .baseUrl("https://dictionary.skyeng.ru/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SkyengApiService::class.java)
    }

    // Repository
    single { TranslationRepository(get(), get()) }

    // ViewModels
    viewModel { TranslationViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
}