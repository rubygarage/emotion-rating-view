package com.dm.emotionrating.library

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDelegate

open class BaseLayout<T : Drawable> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val cache: MutableMap<String, T>

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        context.theme.applyStyle(R.style.EmotionRatingTheme, false)
        cache = mutableMapOf()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cache.clear()
    }

    protected fun getDrawable(key: String, creator: () -> T?): T? {
        return if (cache.containsKey(key)) {
            cache[key]
        } else {
            val drawable = creator()
            if (drawable != null) {
                cache[key] = drawable
            }
            drawable
        }
    }
}