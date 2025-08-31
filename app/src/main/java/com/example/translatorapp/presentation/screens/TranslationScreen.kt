package com.example.translatorapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translatorapp.data.ApiResult
import com.example.translatorapp.di.TranslationViewModelFactory
import com.example.translatorapp.presentation.viewmodels.TranslationViewModel

// presentation/screens/TranslationScreen.kt
@Composable
fun TranslationScreen(
    onNavigateToHistory: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: TranslationViewModel = viewModel(factory = TranslationViewModelFactory(context))
    val uiState by viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Кнопка перехода к истории
        Button(
            onClick = onNavigateToHistory,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("История")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Поле ввода
        OutlinedTextField(
            value = uiState.word,
            onValueChange = { viewModel.onWordChange(it) },
            label = { Text("Введите слово на английском") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка перевода
        Button(
            onClick = { viewModel.translateWord() },
            enabled = uiState.word.isNotBlank() && uiState.result !is ApiResult.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.result is ApiResult.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Перевести")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Результат перевода
        when (val result = uiState.result) {
            is ApiResult.Success -> {
                Text(
                    text = "Перевод: ${result.data.translation}",
                    style = MaterialTheme.typography.bodyLarge
                )
                result.data.transcription?.let {
                    Text("Транскрипция: [$it]")
                }
            }
            is ApiResult.Error -> {
                Text(
                    text = "Ошибка: ${result.message}",
                    color = Color.Red
                )
            }
            ApiResult.Loading -> {
                Text("Загружается...")
            }
            null -> {
                Text("Введите слово для перевода")
            }
        }
    }
}
