package com.example.translatorapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.translatorapp.presentation.screens.FavoritesScreen
import com.example.translatorapp.presentation.screens.HistoryScreen
import com.example.translatorapp.presentation.screens.TranslationScreen

@Composable
fun TranslationNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "translation"
    ) {
        composable("translation") {
            TranslationScreen(
                onNavigateToHistory = { navController.navigate("history") }
            )
        }
        composable("history") {
            HistoryScreen(
                onBack = { navController.popBackStack() },
                onNavigateToFavorites = { navController.navigate("favorites") }
            )
        }
        composable("favorites") {
            FavoritesScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}