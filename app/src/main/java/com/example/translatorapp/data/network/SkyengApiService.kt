package com.example.translatorapp.data.network

import com.example.translatorapp.data.models.SkyengWord
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SkyengApiService {
    @GET("api/public/v1/words/search")
    suspend fun searchWords(
        @Query("search") word: String
    ): Response<List<SkyengWord>>
}