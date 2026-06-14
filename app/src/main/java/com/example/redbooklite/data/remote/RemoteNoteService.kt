package com.example.redbooklite.data.remote

class RemoteNoteService(
    private val api: NoteApiService,
    private val fallbackProvider: () -> List<NoteDto>
) {
    suspend fun fetchFeed(category: String?): RemoteResult<List<NoteDto>> {
        return try {
            val response = api.getFeed(category)
            val items = response.data?.items ?: emptyList()
            if (items.isNotEmpty()) {
                RemoteResult.Success(items)
            } else {
                RemoteResult.Success(fallbackProvider())
            }
        } catch (e: Exception) {
            RemoteResult.Error(e.message ?: "network error")
        }
    }
}
