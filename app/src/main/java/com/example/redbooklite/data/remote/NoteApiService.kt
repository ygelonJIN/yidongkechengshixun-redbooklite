package com.example.redbooklite.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface NoteApiService {
    @GET("feed")
    suspend fun getFeed(@Query("category") category: String? = null): ApiResponse<FeedResponse>
}
