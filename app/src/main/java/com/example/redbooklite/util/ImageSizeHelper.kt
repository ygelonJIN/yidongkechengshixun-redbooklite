package com.example.redbooklite.util

import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import java.io.File

object ImageSizeHelper {

    private const val DEFAULT_RATIO = 0.85f

    fun readAspectRatio(imagePath: String): Float {
        if (imagePath.startsWith("drawable://")) {
            return DEFAULT_RATIO
        }
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeFile(imagePath, options)
        return ratioFromBounds(options.outWidth, options.outHeight)
    }

    fun readAspectRatioFromDrawable(context: Context, @DrawableRes drawableResId: Int): Float {
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeResource(context.resources, drawableResId, options)
        return ratioFromBounds(options.outWidth, options.outHeight)
    }

    fun drawableCoverPath(@DrawableRes drawableResId: Int): String {
        return "drawable://$drawableResId"
    }

    fun readAspectRatio(file: File): Float {
        return readAspectRatio(file.absolutePath)
    }

    private fun ratioFromBounds(width: Int, height: Int): Float {
        if (width <= 0 || height <= 0) return DEFAULT_RATIO
        return width.toFloat() / height.toFloat()
    }
}
