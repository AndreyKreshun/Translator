package com.example.translatorapp.data.models

data class SkyengWord(
    val id: Long,
    val text: String,
    val meanings: List<Meaning>
)

data class Meaning(
    val id: Long,
    val translation: Translation,
    val imageUrl: String?,
    val transcription: String?
)

data class Translation(
    val text: String,
    val note: String?
)