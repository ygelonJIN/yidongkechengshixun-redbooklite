package com.example.redbooklite.data.local

import android.content.Context
import androidx.annotation.DrawableRes
import com.example.redbooklite.R
import com.example.redbooklite.model.Note
import com.example.redbooklite.util.ImageSizeHelper

object SeedData {

    private data class SeedDraft(
        val title: String,
        val content: String,
        @DrawableRes val coverResId: Int,
        val authorName: String,
        val likeCount: Int,
        val minutesAgo: Long
    )

    fun buildNotes(context: Context): List<Note> {
        val now = System.currentTimeMillis()
        return drafts().mapIndexed { index, draft ->
            Note(
                id = (index + 1).toLong(),
                title = draft.title,
                content = draft.content,
                coverPath = ImageSizeHelper.drawableCoverPath(draft.coverResId),
                coverAspectRatio = ImageSizeHelper.readAspectRatioFromDrawable(context, draft.coverResId),
                authorName = draft.authorName,
                likeCount = draft.likeCount,
                isMine = false,
                createdAt = now - 1000 * 60 * draft.minutesAgo
            )
        }
    }

    private fun drafts(): List<SeedDraft> = listOf(
        SeedDraft("外滩打卡！和好友的魔都夏日", "陆家嘴天际线太绝了，江风一吹，什么烦恼都没了。", R.drawable.seed_cover_1, "旅行阿杰", 328, 30),
        SeedDraft("窗外的上海，蓝调时刻太浪漫", "坐在窗边看苏州河上的游船缓缓驶过。", R.drawable.seed_cover_2, "城市漫游者", 512, 90),
        SeedDraft("仿佛走进了一幅水墨画", "晨雾缭绕中的古塔与小桥流水。", R.drawable.seed_cover_3, "古风拾光", 891, 150),
        SeedDraft("这抹蒂芙尼蓝，治愈了整个夏天", "湖水清澈到能看见湖底的木头。", R.drawable.seed_cover_4, "山野旅人", 1204, 210),
        SeedDraft("雪山脚下的蒂芙尼蓝，美到失语", "阳光洒在雪峰上，冰川湖像一颗宝石。", R.drawable.seed_cover_5, "远方日记", 1567, 280),
        SeedDraft("多洛米蒂的黄昏，像童话里的小镇", "尖峰、绿坡、白墙教堂，金色夕阳把整片山谷染暖。", R.drawable.seed_cover_6, "欧洲漫步", 943, 360),
        SeedDraft("周末的夜晚，就要在街头小酌", "树灯、条纹遮阳伞、杯盏碰撞的声响。", R.drawable.seed_cover_7, "慢生活研究所", 426, 420),
        SeedDraft("今日份好心情，简单妆容就出门", "红唇指甲是小小的仪式感。", R.drawable.seed_cover_8, "小梨日记", 612, 480),
        SeedDraft("通勤路上的随手拍，风衣是主角", "米色风衣配金色细链，低调但很耐看。", R.drawable.seed_cover_9, "都市丽人", 389, 540),
        SeedDraft("夜晚微醺，沙发上的松弛感", "暖光、香槟色连衣裙、细高跟。", R.drawable.seed_cover_10, "晚风与酒", 758, 600),
        SeedDraft("樱花季限定，粉色外套太应景了", "桥下的水面落满花瓣，像粉色的地毯。", R.drawable.seed_cover_11, "春日旅拍", 1024, 660),
        SeedDraft("和闺蜜出街，棕色系穿搭好高级", "一个美拉德运动风，一个灰色休闲风。", R.drawable.seed_cover_12, "姐妹穿搭局", 867, 720),
        SeedDraft("紫色球场的少年感，今天很帅", "灰色宽松T恤配红色球鞋，在球场上运球。", R.drawable.seed_cover_13, "球场日记", 531, 780),
        SeedDraft("城市夜色里，随手一拍都好看", "霓虹灯映在脸上，街道还有晚风。", R.drawable.seed_cover_14, "夜行少年", 445, 840),
        SeedDraft("赴一场花约，春天真的来了", "石桥、落樱、浅粉外套，把整个人都衬得温柔。", R.drawable.seed_cover_15, "樱花记事本", 1189, 900),
        SeedDraft("周末逛街，给自己挑一件小首饰", "白色系带上衣配亮闪闪的项链和戒指。", R.drawable.seed_cover_16, "精致日常", 673, 960),
        SeedDraft("上海夜晚街拍，黑色系永远耐看", "路灯、梧桐树、路过的电车。", R.drawable.seed_cover_17, "魔都夜猫子", 902, 1020),
        SeedDraft("公园二十分钟，晒晒太阳就很开心", "白T恤、绿植、斑驳光影。", R.drawable.seed_cover_18, "清爽男孩", 356, 1080)
    )
}
