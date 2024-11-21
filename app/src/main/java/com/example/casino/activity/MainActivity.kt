package com.example.casino.activity

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.activity.ComponentActivity
import com.example.casino.R

class MainActivity : ComponentActivity() {

    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация GestureDetector для обнаружения жестов свайпа
        gestureDetector = GestureDetector(this, SwipeGestureListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    // Метод перехода к экрану игр
    fun toGames(v: View) {
        val intent = Intent(this, Games::class.java)
        startActivity(intent)
    }

    fun toSettings(v: View) {
        val intent = Intent(this, Settings::class.java)
        startActivity(intent)
    }

    // Класс для обработки жестов свайпа
    private inner class SwipeGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (e1 == null || e2 == null) return false
            val diffX = e2.x - e1.x
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX < 0) {
                    // Свайп справа налево - переходим к экрану Games
                    toGames(findViewById(R.id.welcome_text)) // или любой другой вид в макете
                    return true
                }
            }
            return false
        }
    }
}
