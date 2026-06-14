package com.example.redbooklite.ui.author

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.redbooklite.R

class AuthorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.activity_author)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.authorContainer, AuthorFragment())
                .commit()
        }
    }

    companion object {
        const val EXTRA_AUTHOR_NAME = "extra_author_name"
    }
}
