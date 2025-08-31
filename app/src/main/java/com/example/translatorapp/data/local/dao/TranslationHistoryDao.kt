package com.example.translatorapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.translatorapp.data.local.entity.TranslationHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TranslationHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: TranslationHistoryEntity)

    @Query("SELECT * FROM translation_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<TranslationHistoryEntity>>

    @Query("DELETE FROM translation_history WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM translation_history")
    suspend fun clearAll()
}
