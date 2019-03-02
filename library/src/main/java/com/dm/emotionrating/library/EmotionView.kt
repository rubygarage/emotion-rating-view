package com.dm.emotionrating.library

import android.content.Context
import android.util.AttributeSet
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import kotlinx.android.synthetic.main.view_emotion.view.*

class EmotionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseLayout<AnimatedVectorDrawableCompat>(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_emotion, this)
    }

    fun setRating(previousRating: Int, newRating: Int) {
        val animation = getRateAnimation(previousRating, newRating)
        animation?.let {
            val animatedVector = getDrawable(
                it.toString()
            ) { AnimatedVectorDrawableCompat.create(context, it) }
            mouth.setImageDrawable(animatedVector)
            (animatedVector as Animatable2Compat).start()
        }
    }

    private fun getRateAnimation(previousRating: Int, newRating: Int) = when {

        0 == previousRating && 1 == newRating -> R.drawable.animation_mouth_zero_to_one
        0 == previousRating && 2 == newRating -> R.drawable.animation_mouth_zero_to_two
        0 == previousRating && 3 == newRating -> R.drawable.animation_mouth_zero_to_three
        0 == previousRating && 4 == newRating -> R.drawable.animation_mouth_zero_to_four
        0 == previousRating && 5 == newRating -> R.drawable.animation_mouth_zero_to_five

        1 == previousRating && 2 == newRating -> R.drawable.animation_mouth_one_to_two
        1 == previousRating && 3 == newRating -> R.drawable.animation_mouth_one_to_three
        1 == previousRating && 4 == newRating -> R.drawable.animation_mouth_one_to_four
        1 == previousRating && 5 == newRating -> R.drawable.animation_mouth_one_to_five

        2 == previousRating && 1 == newRating -> R.drawable.animation_mouth_two_to_one
        2 == previousRating && 3 == newRating -> R.drawable.animation_mouth_two_to_three
        2 == previousRating && 4 == newRating -> R.drawable.animation_mouth_two_to_four
        2 == previousRating && 5 == newRating -> R.drawable.animation_mouth_two_to_five

        3 == previousRating && 1 == newRating -> R.drawable.animation_mouth_three_to_one
        3 == previousRating && 2 == newRating -> R.drawable.animation_mouth_three_to_two
        3 == previousRating && 4 == newRating -> R.drawable.animation_mouth_three_to_four
        3 == previousRating && 5 == newRating -> R.drawable.animation_mouth_three_to_five

        4 == previousRating && 1 == newRating -> R.drawable.animation_mouth_four_to_one
        4 == previousRating && 2 == newRating -> R.drawable.animation_mouth_four_to_two
        4 == previousRating && 3 == newRating -> R.drawable.animation_mouth_four_to_three
        4 == previousRating && 5 == newRating -> R.drawable.animation_mouth_four_to_five

        5 == previousRating && 1 == newRating -> R.drawable.animation_mouth_five_to_one
        5 == previousRating && 2 == newRating -> R.drawable.animation_mouth_five_to_two
        5 == previousRating && 3 == newRating -> R.drawable.animation_mouth_five_to_three
        5 == previousRating && 4 == newRating -> R.drawable.animation_mouth_five_to_four

        else -> null
    }
}