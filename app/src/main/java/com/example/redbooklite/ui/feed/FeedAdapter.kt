package com.example.redbooklite.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.redbooklite.R

class FeedAdapter(
    private val items: List<FeedNoteUiModel>
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed_note, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvNoteTitle)
        private val content: TextView = itemView.findViewById(R.id.tvNoteContent)
        private val likes: TextView = itemView.findViewById(R.id.tvLikeCount)

        fun bind(item: FeedNoteUiModel) {
            title.text = item.title
            content.text = item.content
            likes.text = item.likes.toString()
            itemView.layoutParams = itemView.layoutParams.apply {
                height = item.height
            }
        }
    }
}
