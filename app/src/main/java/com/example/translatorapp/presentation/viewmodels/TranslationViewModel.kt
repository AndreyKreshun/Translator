package com.example.translatorapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translatorapp.data.ApiResult
import com.example.translatorapp.data.models.TranslationResponse
import com.example.translatorapp.data.repository.TranslationRepository
import kotlinx.coroutines.launch

class TranslationViewModel(
    private val repository: TranslationRepository
) : ViewModel() {
    private val _uiState = mutableStateOf(TranslationUiState())
    val uiState: androidx.compose.runtime.State<TranslationUiState> = _uiState

    fun onWordChange(word: String) {
        _uiState.value = _uiState.value.copy(
            word = word,
            result = null
        )
    }

    fun translateWord() {
        val currentWord = _uiState.value.word.trim()
        if (currentWord.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                result = ApiResult.Loading
            )

            val result = repository.translateWord(currentWord)
            _uiState.value = _uiState.value.copy(
                result = result
            )
        }
    }

    fun clearResult() {
        _uiState.value = _uiState.value.copy(
            result = null
        )
    }
}

data class TranslationUiState(
    val word: String = "",
    val result: ApiResult<TranslationResponse>? = null
)