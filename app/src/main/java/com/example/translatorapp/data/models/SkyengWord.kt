package com.example.translatorapp.data.models

data class SkyengWord(
    val id: Long,
    val text: String,
    val meanings: List<Meaning>
)
