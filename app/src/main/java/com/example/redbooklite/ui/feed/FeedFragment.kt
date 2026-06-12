package com.example.redbooklite.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.redbooklite.R

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val refreshLayout = view.findViewById<androidx.swiperefreshlayout.widget.SwipeRefreshLayout>(R.id.refreshLayout)
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvFeed)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = FeedAdapter(sampleNotes())
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false
            Toast.makeText(requireContext(), "已是最新内容", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sampleNotes(): List<FeedNoteUiModel> = listOf(
        FeedNoteUiModel("城市日落", "周末的天台晚霞", 12, 360),
        FeedNoteUiModel("咖啡记录", "今天喝到一杯很好喝的拿铁", 8, 420),
        FeedNoteUiModel("穿搭分享", "轻松通勤风格", 21, 390),
        FeedNoteUiModel("周末散步", "沿着河边走了很久", 16, 510),
        FeedNoteUiModel("桌面整理", "清爽的桌面让人更专注", 4, 300),
        FeedNoteUiModel("做饭日记", "今晚的番茄牛腩很成功", 19, 460),
        FeedNoteUiModel("书单推荐", "这几本真的很值得读", 7, 330),
        FeedNoteUiModel("拍照练习", "尝试了新的构图方式", 9, 540),
        FeedNoteUiModel("旅行照片", "在海边拍到的瞬间", 13, 410),
        FeedNoteUiModel("健身打卡", "今天完成了力量训练", 6, 350),
        FeedNoteUiModel("收纳技巧", "小物件也能整整齐齐", 3, 290),
        FeedNoteUiModel("音乐分享", "最近循环播放的一首歌", 15, 440)
    )
}
