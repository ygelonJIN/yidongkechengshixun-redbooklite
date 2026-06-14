package com.example.redbooklite.data.remote

class FakeRemoteApi : NoteApiService {
    override suspend fun getFeed(category: String?): ApiResponse<FeedResponse> {
        throw IllegalStateException("Backend not configured")
    }
}
