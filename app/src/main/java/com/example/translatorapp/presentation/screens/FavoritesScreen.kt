package com.example.translatorapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.translatorapp.WordTranslation

// presentation/screens/FavoritesScreen.kt
@Composable
fun FavoritesScreen() {
    // Моковые данные для избранного
    val favorites = remember {
        listOf(
            WordTranslation(1, "hello", "привет", true),
            WordTranslation(3, "android", "андроид", true),
            WordTranslation(5, "compose", "композ", true)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Избранные переводы",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (favorites.isEmpty()) {
            // Сообщение если избранное пустое
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Нет избранных переводов",
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn {
                items(favorites) { translation ->
                    /*TranslationItem(
                        translation = translation,
                        onToggleFavorite = { /* пока пусто */ },
                        onDelete = { /* пока пусто */ }
                    )*/
                }
            }
        }
    }
}