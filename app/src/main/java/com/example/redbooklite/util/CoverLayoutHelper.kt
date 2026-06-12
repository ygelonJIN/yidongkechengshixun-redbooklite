package com.example.redbooklite.util

import android.content.Context
import android.widget.ImageView
import kotlin.math.max

object CoverLayoutHelper {

    fun applyStaggeredCoverHeight(imageView: ImageView, aspectRatio: Float) {
        val safeRatio = max(0.55f, aspectRatio)
        val columnWidth = getColumnWidthPx(imageView.context)
        val coverHeight = (columnWidth / safeRatio).toInt()

        val params = imageView.layoutParams
        params.height = coverHeight
        imageView.layoutParams = params
    }

    private fun getColumnWidthPx(context: Context): Int {
        val dm = context.resources.displayMetrics
        val density = dm.density
        val spacing = ((4f + 4f + 4f + 4f) * density).toInt()
        return (dm.widthPixels - spacing) / 2
    }
}
