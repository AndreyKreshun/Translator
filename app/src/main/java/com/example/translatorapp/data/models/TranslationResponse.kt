package com.example.translatorapp.data.models

data class TranslationResponse(
    val word: String,
    val translation: String,
    val transcription: String? = null,
    val imageUrl: String? = null
)
