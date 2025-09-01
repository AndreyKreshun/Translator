package com.example.translatorapp.data.models

data class Meaning(
    val id: Long,
    val translation: Translation,
    val imageUrl: String?,
    val transcription: String?
)
