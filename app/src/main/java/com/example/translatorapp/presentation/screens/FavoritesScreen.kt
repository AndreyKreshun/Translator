package com.example.translatorapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translatorapp.presentation.card.HistoryItem
import com.example.translatorapp.presentation.viewmodels.FavoritesViewModel
import org.koin.androidx.compose.koinViewModel

// presentation/screens/FavoritesScreen.kt
@Composable
fun FavoritesScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: FavoritesViewModel = koinViewModel()
    val favorites by viewModel.favorites.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBack) {
                Text("Назад")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Избранные переводы",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (favorites.isEmpty()) {
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
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(favorites) { item ->
                    HistoryItem(
                        item = item,
                        onToggleFavorite = { id, isFavorite ->
                            viewModel.toggleFavorite(id, isFavorite)
                        },
                        onDelete = { /* Удаление из избранного через toggle */
                            viewModel.toggleFavorite(item.id, false)
                        }
                    )
                }
            }
        }
    }
}