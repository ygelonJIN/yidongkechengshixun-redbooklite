package com.example.redbooklite.ui.feed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.redbooklite.R
import com.example.redbooklite.RedBookApp
import com.example.redbooklite.model.Note
import com.example.redbooklite.ui.detail.NoteDetailActivity

class FeedFragment : Fragment(), NoteAdapter.OnNoteClickListener {

    private lateinit var viewModel: FeedViewModel
    private lateinit var adapter: NoteAdapter
    private lateinit var rvFeed: RecyclerView
    private lateinit var tvEmpty: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFeed = view.findViewById(R.id.rvFeed)
        tvEmpty = view.findViewById(R.id.tvEmpty)

        val app = requireActivity().application as RedBookApp
        viewModel = ViewModelProvider(this, FeedViewModelFactory(app.repository))[FeedViewModel::class.java]

        adapter = NoteAdapter(this)
        rvFeed.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
            gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        }
        rvFeed.adapter = adapter
        rvFeed.setHasFixedSize(false)

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            render(notes)
        }
    }

    private fun render(notes: List<Note>) {
        adapter.submitList(notes)
        tvEmpty.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
        rvFeed.visibility = if (notes.isEmpty()) View.GONE else View.VISIBLE
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(requireContext(), NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_ID, note.id)
        startActivity(intent)
    }
}
