package com.example.translatorapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translatorapp.data.local.entity.TranslationHistoryEntity
import com.example.translatorapp.data.repository.TranslationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: TranslationRepository) : ViewModel() {
    val history: StateFlow<List<TranslationHistoryEntity>> =
        repository.getHistory().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            repository.deleteHistoryItem(id)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}
