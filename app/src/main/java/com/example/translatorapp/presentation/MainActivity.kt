package com.example.translatorapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.translatorapp.presentation.navigation.TranslationNavGraph
import com.example.translatorapp.presentation.theme.TranslatorAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslatorAppTheme {
                TranslationNavGraph()
            }
        }
    }
}