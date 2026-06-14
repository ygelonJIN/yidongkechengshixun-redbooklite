package com.example.redbooklite.ui.feed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.redbooklite.R
import com.example.redbooklite.RedBookApp
import com.example.redbooklite.model.Note
import com.example.redbooklite.model.NoteCategory
import com.example.redbooklite.ui.detail.NoteDetailActivity
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText

class FeedFragment : Fragment(), NoteAdapter.OnNoteClickListener {

    private lateinit var viewModel: FeedViewModel
    private lateinit var adapter: NoteAdapter
    private lateinit var rvFeed: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var tvError: TextView
    private lateinit var layoutState: View
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    private lateinit var etSearch: TextInputEditText

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
        tvError = view.findViewById(R.id.tvError)
        layoutState = view.findViewById(R.id.layoutState)
        swipeRefresh = view.findViewById(R.id.swipeRefresh)
        etSearch = view.findViewById(R.id.etSearch)
        etSearch.doAfterTextChanged { text ->
            viewModel.search(text?.toString().orEmpty())
        }

        val app = requireActivity().application as RedBookApp
        viewModel = ViewModelProvider(this, FeedViewModelFactory(app.repository))[FeedViewModel::class.java]

        adapter = NoteAdapter(this)
        rvFeed.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
            gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        }
        rvFeed.adapter = adapter
        rvFeed.setHasFixedSize(false)

        bindCategoryChips(view)
        swipeRefresh.setOnRefreshListener { viewModel.loadFeed(forceRefresh = true) }
        tvError.setOnClickListener { viewModel.loadFeed(forceRefresh = false) }
        etSearch.setOnEditorActionListener { _, _, _ ->
            viewModel.search(etSearch.text?.toString().orEmpty())
            true
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.notes)
            swipeRefresh.isRefreshing = state.isRefreshing
            val hasContent = state.notes.isNotEmpty()
            val showLoading = state.isLoading && !hasContent
            val showError = state.errorMessage != null && !hasContent
            val showEmpty = !showLoading && !showError && state.notes.isEmpty()
            layoutState.visibility = if (showLoading || showEmpty || showError) View.VISIBLE else View.GONE
            rvFeed.visibility = if (showLoading || showEmpty || showError) View.GONE else View.VISIBLE
            tvEmpty.visibility = if (showEmpty) View.VISIBLE else View.GONE
            tvError.visibility = if (showError) View.VISIBLE else View.GONE
            tvError.text = state.errorMessage ?: getString(R.string.empty_feed)
            if (!state.isLoading && !state.isRefreshing) {
                view.findViewById<Chip>(R.id.chipAll).isChecked = state.category == NoteCategory.ALL
                view.findViewById<Chip>(R.id.chipTravel).isChecked = state.category == NoteCategory.TRAVEL
                view.findViewById<Chip>(R.id.chipFood).isChecked = state.category == NoteCategory.FOOD
                view.findViewById<Chip>(R.id.chipFashion).isChecked = state.category == NoteCategory.FASHION
            }
        }
    }

    fun refreshFeedAfterPublish() {
        viewModel.loadFeed(forceRefresh = true)
    }

    fun refreshFeed() {
        viewModel.loadFeed(forceRefresh = true)
    }

    private fun bindCategoryChips(view: View) {
        view.findViewById<Chip>(R.id.chipAll).setOnClickListener { viewModel.selectCategory(NoteCategory.ALL) }
        view.findViewById<Chip>(R.id.chipTravel).setOnClickListener { viewModel.selectCategory(NoteCategory.TRAVEL) }
        view.findViewById<Chip>(R.id.chipFood).setOnClickListener { viewModel.selectCategory(NoteCategory.FOOD) }
        view.findViewById<Chip>(R.id.chipFashion).setOnClickListener { viewModel.selectCategory(NoteCategory.FASHION) }
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(requireContext(), NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_ID, note.id)
        startActivity(intent)
    }

    companion object {
        fun newInstance(category: NoteCategory = NoteCategory.ALL): FeedFragment = FeedFragment()
    }
}
