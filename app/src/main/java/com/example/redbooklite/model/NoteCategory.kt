package com.example.redbooklite.model

enum class NoteCategory(val key: String, val displayName: String) {
    ALL("all", "全部"),
    TRAVEL("travel", "travel"),
    FOOD("food", "food"),
    FASHION("fashion", "fashion");

    val apiValue: String
        get() = key

    companion object {
        fun fromKey(key: String?): NoteCategory {
            return entries.firstOrNull { it.key.equals(key, ignoreCase = true) } ?: ALL
        }

        fun fromApiValue(value: String?): NoteCategory = fromKey(value)
    }
}
