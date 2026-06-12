package com.example.redbooklite.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.redbooklite.R
import com.example.redbooklite.RedBookApp
import com.example.redbooklite.util.ImageLoader

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var ivCover: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvContent: TextView
    private lateinit var tvLikeCount: TextView
    private lateinit var btnLike: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.activity_note_detail)

        val noteId = readNoteIdFromIntent()
        if (noteId == INVALID_NOTE_ID) {
            finish()
            return
        }

        initViews()
        initNavigation()
        initViewModel(noteId)
        bindActions()
        observeNote()
        viewModel.loadNote()
    }

    private fun readNoteIdFromIntent(): Long {
        return intent.getLongExtra(EXTRA_NOTE_ID, INVALID_NOTE_ID)
    }

    private fun initViews() {
        ivCover = findViewById(R.id.ivCover)
        tvTitle = findViewById(R.id.tvTitle)
        tvAuthor = findViewById(R.id.tvAuthor)
        tvContent = findViewById(R.id.tvContent)
        tvLikeCount = findViewById(R.id.tvLikeCount)
        btnLike = findViewById(R.id.btnLike)
    }

    private fun initNavigation() {
        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun initViewModel(noteId: Long) {
        val app = application as RedBookApp
        viewModel = ViewModelProvider(
            this,
            DetailViewModelFactory(app.repository, noteId)
        )[DetailViewModel::class.java]
    }

    private fun bindActions() {
        btnLike.setOnClickListener {
            viewModel.likeNote()
        }
    }

    private fun observeNote() {
        viewModel.note.observe(this) { note ->
            if (note == null) {
                finish()
                return@observe
            }
            ImageLoader.loadCover(ivCover, note.coverPath)
            tvTitle.text = note.title
            tvAuthor.text = note.authorName
            tvContent.text = note.content
            tvLikeCount.text = getString(R.string.like_format, note.likeCount)
        }
    }

    companion object {
        const val EXTRA_NOTE_ID = "extra_note_id"
        private const val INVALID_NOTE_ID = -1L
    }
}
