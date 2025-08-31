package com.example.translatorapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.translatorapp.WordTranslation

// presentation/screens/HistoryScreen.kt
@Composable
fun HistoryScreen(
    onNavigateToFavorites: () -> Unit
) {
    // Моковые данные для истории
    val translations = remember {
        listOf(
            WordTranslation(1, "hello", "привет", true),
            WordTranslation(2, "world", "мир", false),
            WordTranslation(3, "android", "андроид", true),
            WordTranslation(4, "kotlin", "котлин", false),
            WordTranslation(5, "compose", "композ", true)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Заголовок и кнопка избранного
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "История переводов"
            )
            Button(onClick = onNavigateToFavorites) {
                Text("Избранное")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Список переводов
        LazyColumn {
            items(translations) { translation ->
                TranslationItem(
                    translation = translation,
                    onToggleFavorite = { /* пока пусто */ },
                    onDelete = { /* пока пусто */ }
                )
            }
        }
    }
}

@Composable
fun TranslationItem(
    translation: WordTranslation,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    translation.word,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    translation.translation
                )

            }

            // Кнопка избранного
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (translation.isFavorite)
                        Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Избранное",
                    tint = if (translation.isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Кнопка удаления
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Удалить",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}