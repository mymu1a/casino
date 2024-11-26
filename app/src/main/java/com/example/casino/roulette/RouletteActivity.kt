package com.example.casino.roulette

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.casino.R
import java.util.Random

class RouletteActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var textView: TextView
    private lateinit var ivWheel: ImageView

    private val random = Random()
    private var degree = 0
    private var degreeOld = 0

    // Because there are 37 sectors on the wheel (9.72 degrees each)
    private val FACTOR = 4.86f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roulette)

        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)
        ivWheel = findViewById(R.id.iv_wheel)

        button.setOnClickListener {
            // Record the old degree
            degreeOld = degree % 360
            // Randomly rotate the wheel
            degree = random.nextInt(3600) + 720

            val rotate = RotateAnimation(
                degreeOld.toFloat(), degree.toFloat(),
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
            ).apply {
                duration = 3600
                fillAfter = true
                interpolator = DecelerateInterpolator()
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                        textView.text = ""
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        textView.text = currentNumber(360 - (degree % 360))
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}
                })
            }

            // Start the animation
            ivWheel.startAnimation(rotate)
        }
    }

    /**
     * Returns the number and color based on the degrees.
     * @param degrees The angle to evaluate.
     * @return Number & color string.
     */
    private fun currentNumber(degrees: Int): String {
        return when {
            degrees in (FACTOR * 1).toInt() until (FACTOR * 3).toInt() -> "32 red"
            degrees in (FACTOR * 3).toInt() until (FACTOR * 5).toInt() -> "15 black"
            degrees in (FACTOR * 5).toInt() until (FACTOR * 7).toInt() -> "19 red"
            degrees in (FACTOR * 7).toInt() until (FACTOR * 9).toInt() -> "4 black"
            degrees in (FACTOR * 9).toInt() until (FACTOR * 11).toInt() -> "21 red"
            degrees in (FACTOR * 11).toInt() until (FACTOR * 13).toInt() -> "2 black"
            degrees in (FACTOR * 13).toInt() until (FACTOR * 15).toInt() -> "25 red"
            degrees in (FACTOR * 15).toInt() until (FACTOR * 17).toInt() -> "17 black"
            degrees in (FACTOR * 17).toInt() until (FACTOR * 19).toInt() -> "34 red"
            degrees in (FACTOR * 19).toInt() until (FACTOR * 21).toInt() -> "6 black"
            degrees in (FACTOR * 21).toInt() until (FACTOR * 23).toInt() -> "21 red"
            degrees in (FACTOR * 23).toInt() until (FACTOR * 25).toInt() -> "13 black"
            degrees in (FACTOR * 25).toInt() until (FACTOR * 27).toInt() -> "36 red"
            degrees in (FACTOR * 27).toInt() until (FACTOR * 29).toInt() -> "11 black"
            degrees in (FACTOR * 29).toInt() until (FACTOR * 31).toInt() -> "30 red"
            degrees in (FACTOR * 31).toInt() until (FACTOR * 33).toInt() -> "8 black"
            degrees in (FACTOR * 33).toInt() until (FACTOR * 35).toInt() -> "23 red"
            degrees in (FACTOR * 35).toInt() until (FACTOR * 37).toInt() -> "10 black"
            degrees in (FACTOR * 37).toInt() until (FACTOR * 39).toInt() -> "5 red"
            degrees in (FACTOR * 39).toInt() until (FACTOR * 41).toInt() -> "24 black"
            degrees in (FACTOR * 41).toInt() until (FACTOR * 43).toInt() -> "16 red"
            degrees in (FACTOR * 43).toInt() until (FACTOR * 45).toInt() -> "33 black"
            degrees in (FACTOR * 45).toInt() until (FACTOR * 47).toInt() -> "1 red"
            degrees in (FACTOR * 47).toInt() until (FACTOR * 49).toInt() -> "20 black"
            degrees in (FACTOR * 49).toInt() until (FACTOR * 51).toInt() -> "14 red"
            degrees in (FACTOR * 51).toInt() until (FACTOR * 53).toInt() -> "31 black"
            degrees in (FACTOR * 53).toInt() until (FACTOR * 55).toInt() -> "9 red"
            degrees in (FACTOR * 55).toInt() until (FACTOR * 57).toInt() -> "22 black"
            degrees in (FACTOR * 57).toInt() until (FACTOR * 59).toInt() -> "18 red"
            degrees in (FACTOR * 59).toInt() until (FACTOR * 61).toInt() -> "29 black"
            degrees in (FACTOR * 61).toInt() until (FACTOR * 63).toInt() -> "7 red"
            degrees in (FACTOR * 63).toInt() until (FACTOR * 65).toInt() -> "28 black"
            degrees in (FACTOR * 65).toInt() until (FACTOR * 67).toInt() -> "12 red"
            degrees in (FACTOR * 67).toInt() until (FACTOR * 69).toInt() -> "35 black"
            degrees in (FACTOR * 69).toInt() until (FACTOR * 71).toInt() -> "3 red"
            degrees in (FACTOR * 71).toInt() until (FACTOR * 73).toInt() -> "26 black"
            degrees in (FACTOR * 73).toInt() until 360 || degrees in 0 until (FACTOR * 1).toInt() -> "0"
            else -> ""
        }
    }
}