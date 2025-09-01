package com.example.translatorapp.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.translatorapp.data.local.TranslationDatabase
import com.example.translatorapp.data.network.SkyengApiService
import com.example.translatorapp.data.repository.TranslationRepository
import com.example.translatorapp.presentation.viewmodels.FavoritesViewModel
import com.example.translatorapp.presentation.viewmodels.HistoryViewModel
import com.example.translatorapp.presentation.viewmodels.TranslationViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HistoryViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val apiService = Retrofit.Builder()
            .baseUrl("https://dictionary.skyeng.ru/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SkyengApiService::class.java)

        val db = Room.databaseBuilder(
            context.applicationContext,
            TranslationDatabase::class.java,
            "translation_db"
        ).build()

        val repository = TranslationRepository(apiService, db.historyDao())

        return when {
            modelClass.isAssignableFrom(TranslationViewModel::class.java) ->
                TranslationViewModel(repository) as T
            modelClass.isAssignableFrom(HistoryViewModel::class.java) ->
                HistoryViewModel(repository) as T
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) ->
                FavoritesViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
