package com.example.redbooklite.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface FeedApiService {
    @GET("notes")
    suspend fun getNotes(
        @Query("category") category: String? = null
    ): List<NoteDto>
}
