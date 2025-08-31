package com.example.translatorapp.di

// di/ViewModelFactory.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.translatorapp.data.network.SkyengApiService
import com.example.translatorapp.data.repository.TranslationRepository
import com.example.translatorapp.presentation.viewmodels.TranslationViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class TranslationViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TranslationViewModel::class.java)) {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val apiService = Retrofit.Builder()
                .baseUrl("https://dictionary.skyeng.ru/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(SkyengApiService::class.java)

            val repository = TranslationRepository(apiService)
            return TranslationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}