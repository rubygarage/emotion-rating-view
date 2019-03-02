package com.dm.emotionrating.library

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.dm.emotionrating.library.Constant.MAX_RATING
import com.dm.emotionrating.library.Constant.MIN_RATING

class RatingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseLayout<AnimatedVectorDrawableCompat>(context, attrs, defStyleAttr),
    View.OnClickListener {

    private val collapseToExpandAnimation = R.drawable.animation_grade_collapse_to_expand
    private val expandToCollapseAnimation = R.drawable.animation_grade_expand_to_collapse
    private val gradeContainer: LinearLayout
    private var ratingChangeListener: RatingChangeListener? = null
    private var lastClickedPosition = -1
    private var isClickBlocked = false
    private val animationCallback = object : Animatable2Compat.AnimationCallback() {
        override fun onAnimationStart(drawable: Drawable?) {
            isClickBlocked = true
        }

        override fun onAnimationEnd(drawable: Drawable?) {
            isClickBlocked = false
        }
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingView)
        val height = typedArray.getDimensionPixelSize(
            R.styleable.RatingView_android_layout_height,
            WRAP_CONTENT
        )
        val backgroundRes = typedArray.getResourceId(
            R.styleable.RatingView_android_background,
            R.drawable.background_rating_view
        )
        val gap = typedArray.getDimensionPixelSize(R.styleable.RatingView_gap, 0)
        typedArray.recycle()

        layoutParams = LayoutParams(WRAP_CONTENT, height)
        gradeContainer = LinearLayout(context)
        gradeContainer.orientation = HORIZONTAL
        gradeContainer.layoutParams = LayoutParams(WRAP_CONTENT, height)
        addView(gradeContainer)
        setBackgroundResource(backgroundRes)

        addGrades(height, gap)
    }

    fun setRatingChangeListener(listener: (previousRating: Int, newRating: Int) -> Unit) {
        ratingChangeListener = object : RatingChangeListener {
            override fun onRatingChanged(previousRating: Int, newRating: Int) {
                listener(previousRating, newRating)
            }
        }
    }

    fun getCurrentRating() = lastClickedPosition + 1

    fun setRating(rating: Int) {
        if (rating in MIN_RATING..MAX_RATING) {
            selectPosition(rating - 1)
        }
    }

    override fun onClick(view: View?) {
        val selectedPosition = gradeContainer.indexOfChild(view)
        selectPosition(selectedPosition)
    }

    private fun selectPosition(position: Int) {
        if (!isClickBlocked && lastClickedPosition != position) {
            ratingChangeListener?.onRatingChanged(lastClickedPosition + 1, position + 1)
            lastClickedPosition = position
            animateGrades(position)
        }
    }

    private fun addGrades(parentHeight: Int, gapSize: Int) {
        for (i in MIN_RATING..MAX_RATING) {
            val grade = ImageView(context)
            val gradeSize = parentHeight - paddingTop - paddingBottom
            val gradeLayoutParams = LayoutParams(gradeSize, gradeSize)
            val margin = if (i < MAX_RATING) gapSize else 0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                gradeLayoutParams.marginEnd = margin
            } else {
                gradeLayoutParams.rightMargin = margin
            }
            grade.layoutParams = gradeLayoutParams
            grade.setImageDrawable(
                getDrawable(getCacheKey(grade, collapseToExpandAnimation)) {
                    AnimatedVectorDrawableCompat.create(
                        context,
                        collapseToExpandAnimation
                    )
                }
            )
            grade.setOnClickListener(this)
            gradeContainer.addView(grade)
        }
    }

    private fun animateGrades(selectedPosition: Int) {
        val childCount = gradeContainer.childCount
        if (selectedPosition < childCount) {
            for (i in 0..childCount) {
                val grade = gradeContainer.getChildAt(i) as? ImageView
                if (grade != null) {
                    val oldVectorResource = grade.tag
                    val newVectorResource = if (i <= selectedPosition) {
                        collapseToExpandAnimation
                    } else {
                        expandToCollapseAnimation
                    }

                    val isResourceChanged = newVectorResource != oldVectorResource
                    val isResourceAnimated = !(oldVectorResource == null &&
                            newVectorResource == expandToCollapseAnimation)

                    if (isResourceChanged && isResourceAnimated) {
                        val animatedVector = getDrawable(
                            getCacheKey(grade, newVectorResource)
                        ) { AnimatedVectorDrawableCompat.create(context, newVectorResource) }
                        grade.tag = newVectorResource
                        grade.setImageDrawable(animatedVector)
                        (animatedVector as? Animatable2Compat)?.let {
                            it.registerAnimationCallback(animationCallback)
                            it.start()
                        }
                    }
                }
            }
        }
    }

    private fun getCacheKey(view: View, resource: Int) = "$view $resource"

    interface RatingChangeListener {

        fun onRatingChanged(previousRating: Int, newRating: Int)
    }
}