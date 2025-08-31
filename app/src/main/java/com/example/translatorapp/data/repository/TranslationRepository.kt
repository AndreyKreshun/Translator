package com.example.translatorapp.data.repository

import com.example.translatorapp.data.ApiResult
import com.example.translatorapp.data.models.TranslationResponse
import com.example.translatorapp.data.network.SkyengApiService

// data/repository/TranslationRepository.kt
class TranslationRepository(private val apiService: SkyengApiService) {
    suspend fun translateWord(word: String): ApiResult<TranslationResponse> {
        return try {
            val response = apiService.searchWords(word)

            if (response.isSuccessful && response.body() != null) {
                val words = response.body()!!

                if (words.isEmpty()) {
                    ApiResult.Error("Слово не найдено")
                } else {
                    // Берем первое слово и первый вариант перевода
                    val firstWord = words.first()
                    val firstMeaning = firstWord.meanings.firstOrNull()

                    if (firstMeaning != null) {
                        val translationResponse = TranslationResponse(
                            word = firstWord.text,
                            translation = firstMeaning.translation.text,
                            transcription = firstMeaning.transcription,
                            imageUrl = firstMeaning.imageUrl
                        )
                        ApiResult.Success(translationResponse)
                    } else {
                        ApiResult.Error("Перевод не найден")
                    }
                }
            } else {
                ApiResult.Error("Ошибка API: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Ошибка сети")
        }
    }
}