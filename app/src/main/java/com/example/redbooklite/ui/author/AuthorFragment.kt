package com.example.redbooklite.ui.author

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redbooklite.R
import com.example.redbooklite.RedBookApp
import com.example.redbooklite.model.Note
import com.example.redbooklite.ui.feed.NoteAdapter
import com.example.redbooklite.util.ImageLoader

class AuthorFragment : Fragment(), NoteAdapter.OnNoteClickListener {

    private lateinit var rvAuthorNotes: RecyclerView
    private lateinit var tvAuthorName: TextView
    private lateinit var tvAuthorEmpty: TextView
    private lateinit var ivAuthorAvatar: ImageView
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_author, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvAuthorNotes = view.findViewById(R.id.rvAuthorNotes)
        tvAuthorName = view.findViewById(R.id.tvAuthorName)
        tvAuthorEmpty = view.findViewById(R.id.tvAuthorEmpty)
        ivAuthorAvatar = view.findViewById(R.id.ivAuthorAvatar)

        adapter = NoteAdapter(this)
        rvAuthorNotes.layoutManager = LinearLayoutManager(requireContext())
        rvAuthorNotes.adapter = adapter

        val app = requireActivity().application as RedBookApp
        val notes = app.repository.getFeedNotes().value
        val authorName = requireActivity().intent.getStringExtra(AuthorActivity.EXTRA_AUTHOR_NAME)
            ?: notes.firstOrNull()?.authorName
            ?: getString(R.string.author_me)
        tvAuthorName.text = authorName
        val authorNotes = notes.filter { it.authorName == authorName }
        val cover = authorNotes.firstOrNull()?.avatarPath ?: authorNotes.firstOrNull()?.coverPath.orEmpty()
        if (cover.isNotBlank()) ImageLoader.loadCover(ivAuthorAvatar, cover)
        adapter.submitList(authorNotes)
        tvAuthorEmpty.visibility = if (authorNotes.isEmpty()) View.VISIBLE else View.GONE
        rvAuthorNotes.visibility = if (authorNotes.isEmpty()) View.GONE else View.VISIBLE
    }

    override fun onNoteClick(note: Note) {
        // 可按需跳转详情页
    }
}
