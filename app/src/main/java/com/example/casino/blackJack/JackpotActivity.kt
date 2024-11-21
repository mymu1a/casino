package com.example.casino.blackJack

import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.casino.R

class JackpotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jackpot)

        val jackpotText: TextView = findViewById(R.id.jackpot_text)
        val counterText: TextView = findViewById(R.id.counter_text)

        // Анимация счета
        val animator = ValueAnimator.ofInt(0, 1000)
        animator.duration = 3000
        animator.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            counterText.text = value.toString()
        }
        animator.start()
    }
}
