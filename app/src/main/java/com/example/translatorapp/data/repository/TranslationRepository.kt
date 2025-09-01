package com.example.translatorapp.data.repository

import com.example.translatorapp.data.ApiResult
import com.example.translatorapp.data.local.dao.TranslationHistoryDao
import com.example.translatorapp.data.local.entity.TranslationHistoryEntity
import com.example.translatorapp.data.models.TranslationResponse
import com.example.translatorapp.data.network.SkyengApiService
import kotlinx.coroutines.flow.Flow

// data/repository/TranslationRepository.kt
class TranslationRepository(
    private val apiService: SkyengApiService,
    private val historyDao: TranslationHistoryDao
) {
    suspend fun translateWord(word: String): ApiResult<TranslationResponse> {
        return try {
            val response = apiService.searchWords(word)

            if (response.isSuccessful && response.body() != null) {
                val words = response.body()!!

                if (words.isEmpty()) {
                    ApiResult.Error("Слово не найдено")
                } else {
                    val firstWord = words.first()
                    val firstMeaning = firstWord.meanings.firstOrNull()

                    if (firstMeaning != null) {
                        val translationResponse = TranslationResponse(
                            word = firstWord.text,
                            translation = firstMeaning.translation.text,
                            transcription = firstMeaning.transcription,
                            imageUrl = firstMeaning.imageUrl
                        )

                        // Сохраняем в историю
                        historyDao.insert(
                            TranslationHistoryEntity(
                                word = translationResponse.word,
                                translation = translationResponse.translation,
                                transcription = translationResponse.transcription
                            )
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

    fun getHistory(): Flow<List<TranslationHistoryEntity>> =
        historyDao.getAllHistory()

    suspend fun deleteHistoryItem(id: Int) = historyDao.deleteById(id)
    suspend fun clearHistory() = historyDao.clearAll()

    fun getFavorites(): Flow<List<TranslationHistoryEntity>> =
        historyDao.getFavorites()

    suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        historyDao.updateFavoriteStatus(id, isFavorite)
    }
}
