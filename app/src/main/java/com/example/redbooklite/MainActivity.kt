package com.example.redbooklite

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.example.redbooklite.ui.feed.FeedFragment
import com.example.redbooklite.ui.market.MarketFragment
import com.example.redbooklite.ui.profile.ProfileFragment
import com.example.redbooklite.ui.publish.PublishActivity

class MainActivity : AppCompatActivity() {

    private lateinit var pageTitle: TextView
    private lateinit var tabHome: TextView
    private lateinit var tabMarket: TextView
    private lateinit var tabMessage: TextView
    private lateinit var tabMe: TextView

    private val feedFragment by lazy { FeedFragment() }
    private val marketFragment by lazy { MarketFragment() }
    private val profileFragment by lazy { ProfileFragment() }

    companion object {
        private const val REQ_PUBLISH = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindow()
        setContentView(R.layout.activity_main)
        bindViews()
        bindListeners()
        if (savedInstanceState == null) {
            showHome()
        }
    }

    private fun setupWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }

    private fun bindViews() {
        pageTitle = findViewById(R.id.tvPageTitle)
        tabHome = findViewById(R.id.tvTabHome)
        tabMarket = findViewById(R.id.tvTabMarket)
        tabMessage = findViewById(R.id.tvTabMessage)
        tabMe = findViewById(R.id.tvTabMe)
    }

    private fun bindListeners() {
        tabHome.setOnClickListener { showHome() }
        tabMarket.setOnClickListener { showMarket() }
        findViewById<android.view.View>(R.id.tabPublish).setOnClickListener {
            startActivityForResult(Intent(this, PublishActivity::class.java), REQ_PUBLISH)
        }
        findViewById<android.view.View>(R.id.tabMessage).setOnClickListener {
            showToast(R.string.developing_toast)
        }
        tabMe.setOnClickListener { showProfile() }
    }

    private fun showHome() {
        showFragment(feedFragment, getString(R.string.feed_title))
        updateTabStyle(Tab.HOME)
    }

    private fun showMarket() {
        showFragment(marketFragment, getString(R.string.tab_market))
        updateTabStyle(Tab.MARKET)
    }

    private fun showProfile() {
        showFragment(profileFragment, getString(R.string.profile_title))
        updateTabStyle(Tab.ME)
    }

    private fun showFragment(fragment: Fragment, title: String) {
        pageTitle.text = title
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PUBLISH && resultCode == RESULT_OK) {
            feedFragment.refreshFeedAfterPublish()
            showHome()
        }
    }

    private fun updateTabStyle(selected: Tab) {
        val activeColor = ContextCompat.getColor(this, R.color.text_primary)
        val inactiveColor = ContextCompat.getColor(this, R.color.nav_inactive)

        setTabStyle(tabHome, selected == Tab.HOME, activeColor, inactiveColor)
        setTabStyle(tabMarket, selected == Tab.MARKET, activeColor, inactiveColor)
        setTabStyle(tabMessage, false, activeColor, inactiveColor)
        setTabStyle(tabMe, selected == Tab.ME, activeColor, inactiveColor)
    }

    private fun setTabStyle(textView: TextView, selected: Boolean, activeColor: Int, inactiveColor: Int) {
        textView.setTextColor(if (selected) activeColor else inactiveColor)
        textView.setTypeface(null, if (selected) Typeface.BOLD else Typeface.NORMAL)
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private enum class Tab {
        HOME, MARKET, ME
    }
}
