package com.example.redbooklite.data.local

import android.content.Context

class LikeStore(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isLiked(noteId: Long): Boolean {
        return prefs.getStringSet(KEY_LIKED_SET, emptySet())?.contains(noteId.toString()) == true
    }

    fun like(noteId: Long) {
        val current = prefs.getStringSet(KEY_LIKED_SET, emptySet())?.toMutableSet() ?: mutableSetOf()
        current.add(noteId.toString())
        prefs.edit().putStringSet(KEY_LIKED_SET, current).apply()
    }

    companion object {
        private const val PREFS_NAME = "like_store"
        private const val KEY_LIKED_SET = "liked_note_ids"
    }
}
