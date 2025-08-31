package com.example.translatorapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.translatorapp.data.local.dao.TranslationHistoryDao
import com.example.translatorapp.data.local.entity.TranslationHistoryEntity

@Database(entities = [TranslationHistoryEntity::class], version = 1, exportSchema = false)
abstract class TranslationDatabase : RoomDatabase() {
    abstract fun historyDao(): TranslationHistoryDao
}
