package com.example.redbooklite.ui.publish

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.redbooklite.R
import com.example.redbooklite.RedBookApp
import com.example.redbooklite.model.Note
import com.example.redbooklite.model.NoteCategory
import com.example.redbooklite.util.ImageLoader
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText

class PublishActivity : AppCompatActivity() {

    private lateinit var etTitle: TextInputEditText
    private lateinit var etContent: TextInputEditText
    private lateinit var etCoverPath: TextInputEditText
    private lateinit var rgCategory: ChipGroup
    private lateinit var ivPreview: ImageView
    private lateinit var btnPublish: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish)

        val app = application as RedBookApp

        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        etCoverPath = findViewById(R.id.etCoverPath)
        rgCategory = findViewById(R.id.rgCategory)
        ivPreview = findViewById(R.id.ivPreview)
        btnPublish = findViewById(R.id.btnPublish)

        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }
        etCoverPath.setText("drawable://${R.drawable.seed_cover_1}")
        ImageLoader.loadCover(ivPreview, etCoverPath.text?.toString().orEmpty())
        etCoverPath.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                ImageLoader.loadCover(ivPreview, s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        btnPublish.setOnClickListener {
            val title = etTitle.text?.toString().orEmpty().trim()
            val content = etContent.text?.toString().orEmpty().trim()
            val coverPath = etCoverPath.text?.toString().orEmpty().trim()
            val category = when (rgCategory.checkedChipId) {
                R.id.rbFood -> NoteCategory.FOOD
                R.id.rbFashion -> NoteCategory.FASHION
                else -> NoteCategory.TRAVEL
            }

            when {
                title.isBlank() -> toast(R.string.publish_need_title)
                content.isBlank() -> toast(R.string.publish_need_content)
                coverPath.isBlank() -> toast(R.string.publish_need_cover)
                else -> {
                    val note = Note(
                        id = app.repository.createNextNoteId(),
                        title = title,
                        content = content,
                        coverPath = coverPath,
                        coverAspectRatio = 1f,
                        authorName = app.repository.getAuthorMe(),
                        avatarPath = coverPath,
                        likeCount = 0,
                        isLiked = false,
                        isMine = true,
                        createdAt = System.currentTimeMillis(),
                        category = category
                    )
                    app.repository.publishNote(note)
                    toast(R.string.publish_success)
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }

    private fun toast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }
}
