package com.example.redbooklite.data.repository

import android.content.Context
import com.example.redbooklite.R
import com.example.redbooklite.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NoteRepository(private val context: Context) {

    private val notesFlow = MutableStateFlow(seedNotes())

    fun getFeedNotes(): Flow<List<Note>> = notesFlow

    private fun seedNotes(): List<Note> {
        val authorMe = context.getString(R.string.author_me)
        return listOf(
            Note(1, "城市日落", "周末的天台晚霞。", "", 0.78f, authorMe, 12, false, 1L),
            Note(2, "咖啡记录", "今天喝到一杯很好喝的拿铁。", "", 1.25f, authorMe, 8, false, 2L),
            Note(3, "穿搭分享", "轻松通勤风格。", "", 0.92f, authorMe, 21, false, 3L),
            Note(4, "周末散步", "沿着河边走了很久。", "", 1.40f, authorMe, 16, false, 4L),
            Note(5, "桌面整理", "清爽的桌面让人更专注。", "", 0.86f, authorMe, 4, false, 5L),
            Note(6, "做饭日记", "今晚的番茄牛腩很成功。", "", 1.10f, authorMe, 19, false, 6L),
            Note(7, "书单推荐", "这几本真的很值得读。", "", 0.75f, authorMe, 7, false, 7L),
            Note(8, "拍照练习", "尝试了新的构图方式。", "", 1.36f, authorMe, 9, false, 8L),
            Note(9, "旅行照片", "在海边拍到的瞬间。", "", 0.98f, authorMe, 13, false, 9L),
            Note(10, "健身打卡", "今天完成了力量训练。", "", 1.18f, authorMe, 6, false, 10L),
            Note(11, "收纳技巧", "小物件也能整整齐齐。", "", 0.84f, authorMe, 3, false, 11L),
            Note(12, "音乐分享", "最近循环播放的一首歌。", "", 1.28f, authorMe, 15, false, 12L),
            Note(13, "晨跑记录", "早上的空气很舒服。", "", 0.88f, authorMe, 18, false, 13L),
            Note(14, "展览随拍", "灯光和色彩都很棒。", "", 1.33f, authorMe, 5, false, 14L),
            Note(15, "夜景散步", "城市夜景很治愈。", "", 1.06f, authorMe, 11, false, 15L),
            Note(16, "桌面好物", "提升效率的小工具。", "", 0.81f, authorMe, 9, false, 16L),
            Note(17, "午后甜点", "这块蛋糕真的很好吃。", "", 1.20f, authorMe, 14, false, 17L),
            Note(18, "书桌一角", "一个安静的角落。", "", 0.95f, authorMe, 10, false, 18L)
        )
    }
}
