package com.dm.emotionrating

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dm.emotionrating.library.Constant.ZERO_RATING
import kotlinx.android.synthetic.main.activity_rating.*

class RatingActivity : AppCompatActivity() {

    companion object {
        private const val IS_DEFAULT_THEME = "is_default_theme"
        private const val RATING = "rating"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isDefaultTheme = preferences.getBoolean(IS_DEFAULT_THEME, true)
        setTheme(if (isDefaultTheme) R.style.AppTheme else R.style.RubyGarageTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        ratingView.setRatingChangeListener { previousRating, newRating ->
            emotionView.setRating(previousRating, newRating)
            gradientBackgroundView.changeBackground(previousRating, newRating)
            submitButton.isEnabled = newRating > ZERO_RATING
        }
        savedInstanceState?.getInt(RATING)?.let { ratingView.setRating(it) }

        skipButton.setOnClickListener {
            Toast.makeText(this, R.string.skip_button_clicked, Toast.LENGTH_SHORT).show()
        }

        submitButton.setOnClickListener {
            Toast.makeText(
                this,
                getString(R.string.submit_button_clicked, ratingView.getCurrentRating()),
                Toast.LENGTH_SHORT
            ).show()
            preferences.edit().putBoolean(IS_DEFAULT_THEME, !isDefaultTheme).apply()
            finish()
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(RATING, ratingView.getCurrentRating())
    }
}
