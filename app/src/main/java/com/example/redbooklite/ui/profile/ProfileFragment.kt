package com.example.redbooklite.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redbooklite.R
import com.example.redbooklite.RedBookApp
import com.example.redbooklite.model.Note
import com.example.redbooklite.ui.detail.NoteDetailActivity
import com.example.redbooklite.ui.feed.NoteAdapter

class ProfileFragment : Fragment(), NoteAdapter.OnNoteClickListener {

    private lateinit var rvMine: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var tvNotesCount: TextView
    private lateinit var tvLikesCount: TextView
    private lateinit var tvFavoritesCount: TextView
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMine = view.findViewById(R.id.rvMine)
        tvEmpty = view.findViewById(R.id.tvProfileEmpty)
        tvNotesCount = view.findViewById(R.id.tvProfileNotesCount)
        tvLikesCount = view.findViewById(R.id.tvProfileLikesCount)
        tvFavoritesCount = view.findViewById(R.id.tvProfileFavoritesCount)

        adapter = NoteAdapter(this)
        rvMine.layoutManager = LinearLayoutManager(requireContext())
        rvMine.adapter = adapter

        loadMineNotes()
    }

    override fun onResume() {
        super.onResume()
        if (view != null) loadMineNotes()
    }

    private fun loadMineNotes() {
        val app = requireActivity().application as RedBookApp
        val notes = app.repository.getFeedNotes().value
        val mineNotes = notes.filter { it.isMine }
        val likes = notes.count { it.isLiked }
        val favorites = mineNotes.size
        tvNotesCount.text = mineNotes.size.toString()
        tvLikesCount.text = likes.toString()
        tvFavoritesCount.text = favorites.toString()
        adapter.submitList(mineNotes)
        tvEmpty.visibility = if (mineNotes.isEmpty()) View.VISIBLE else View.GONE
        rvMine.visibility = if (mineNotes.isEmpty()) View.GONE else View.VISIBLE
    }

    override fun onNoteClick(note: Note) {
        startActivity(Intent(requireContext(), NoteDetailActivity::class.java).apply {
            putExtra(NoteDetailActivity.EXTRA_NOTE_ID, note.id)
        })
    }
}
