package com.example.redbooklite.ui.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redbooklite.R
import com.example.redbooklite.RedBookApp
import com.example.redbooklite.model.Note
import com.example.redbooklite.ui.feed.NoteAdapter

class MarketFragment : Fragment() {

    private lateinit var rvMarket: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_market, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMarket = view.findViewById(R.id.rvMarket)
        tvEmpty = view.findViewById(R.id.tvMarketEmpty)
        adapter = NoteAdapter(object : NoteAdapter.OnNoteClickListener {
            override fun onNoteClick(note: Note) = Unit
        })
        rvMarket.layoutManager = GridLayoutManager(requireContext(), 2)
        rvMarket.adapter = adapter

        val app = requireActivity().application as RedBookApp
        val notes = app.repository.getFeedNotes().value
        val marketNotes = notes.take(8).shuffled()
        adapter.submitList(marketNotes)
        tvEmpty.visibility = if (marketNotes.isEmpty()) View.VISIBLE else View.GONE
        rvMarket.visibility = if (marketNotes.isEmpty()) View.GONE else View.VISIBLE
    }
}
