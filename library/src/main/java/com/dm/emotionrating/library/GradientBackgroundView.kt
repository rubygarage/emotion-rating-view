package com.dm.emotionrating.library

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.dm.emotionrating.library.Constant.MAX_RATING
import com.dm.emotionrating.library.Constant.ZERO_RATING

class GradientBackgroundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseLayout<GradientDrawable>(context, attrs, defStyleAttr) {

    private val gradeGradients: List<IntArray>
    private val duration: Long
    private val gradientView: View

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientBackgroundView)
        val zeroGradeGradientColors = typedArray.getIntArray(
            R.styleable.GradientBackgroundView_zeroGradeGradientColors,
            R.array.defaultZeroGradeGradientColors
        )
        val oneGradeGradientColors = typedArray.getIntArray(
            R.styleable.GradientBackgroundView_oneGradeGradientColors,
            R.array.defaultOneGradeGradientColors
        )
        val twoGradeGradientColors = typedArray.getIntArray(
            R.styleable.GradientBackgroundView_twoGradeGradientColors,
            R.array.defaultTwoGradeGradientColors
        )
        val threeGradeGradientColors = typedArray.getIntArray(
            R.styleable.GradientBackgroundView_threeGradeGradientColors,
            R.array.defaultThreeGradeGradientColors
        )
        val fourGradeGradientColors = typedArray.getIntArray(
            R.styleable.GradientBackgroundView_fourGradeGradientColors,
            R.array.defaultFourGradeGradientColors
        )
        val fiveGradeGradientColors = typedArray.getIntArray(
            R.styleable.GradientBackgroundView_fiveGradeGradientColors,
            R.array.defaultFiveGradeGradientColors
        )
        typedArray.recycle()

        gradeGradients = listOf(
            zeroGradeGradientColors, oneGradeGradientColors, twoGradeGradientColors,
            threeGradeGradientColors, fourGradeGradientColors, fiveGradeGradientColors
        )

        duration = resources.getInteger(R.integer.animation_duration).toLong()

        background = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            getGradientColors(ZERO_RATING)
        )

        gradientView = View(context)
        gradientView.layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        addView(gradientView)
    }

    fun changeBackground(previousRating: Int, newRating: Int) {
        val previousGradientColors = getGradientColors(previousRating)
        val newGradientColors = getGradientColors(newRating)

        val firstGradient = getDrawable(
            previousRating.toString()
        ) { GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, previousGradientColors) }
        val secondGradient = getDrawable(
            newRating.toString()
        ) { GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, newGradientColors) }

        gradientView.alpha = 0f
        gradientView.animate()
            .setDuration(duration)
            .alpha(1f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    background = secondGradient
                    animation.removeAllListeners()
                }

                override fun onAnimationStart(animation: Animator) {
                    background = firstGradient
                    gradientView.background = secondGradient
                }
            })
    }

    private fun TypedArray.getIntArray(index: Int, defaultValue: Int): IntArray {
        val arrayId = getResourceId(index, defaultValue)
        return resources.getIntArray(arrayId)
    }

    private fun getGradientColors(rating: Int) = gradeGradients[rating.checkRating()]

    private fun Int.checkRating() = if (this in ZERO_RATING..MAX_RATING) this else ZERO_RATING
}