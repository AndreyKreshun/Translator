package com.example.translatorapp

// data/models/WordTranslation.kt
data class WordTranslation(
    val id: Int = 0,
    val word: String,
    val translation: String,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
