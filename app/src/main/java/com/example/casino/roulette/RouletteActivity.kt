package com.example.casino.roulette

import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.casino.R
import com.github.jinatonic.confetti.CommonConfetti
import java.util.Random

class RouletteActivity : AppCompatActivity() {

    private lateinit var spinButton: Button
    private lateinit var redBetButton: Button
    private lateinit var blackBetButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var ivWheel: ImageView
    private lateinit var resultMessage: TextView
    private lateinit var betButtons: GridLayout

    private val random = Random()
    private var degree = 0
    private var degreeOld = 0
    private var selectedBet: Int? = null // Выбранное число для ставки
    private var selectedColorBet: String? = null // Ставка на цвет (red/black)

    private val FACTOR = 4.86f // Угол сектора колеса

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roulette)

        // Привязка виджетов к переменным
        spinButton = findViewById(R.id.btn_spin)
        redBetButton = findViewById(R.id.btn_redd)
        blackBetButton = findViewById(R.id.btn_black)
        resultMessage = findViewById(R.id.result_message)
        ivWheel = findViewById(R.id.iv_wheel)
        betButtons = findViewById(R.id.bet_buttons)

        // Устанавливаем обработчик нажатий для кнопок ставок
        setupBetButtons()

        redBetButton.setOnClickListener {
            selectedColorBet = "red"
            selectedBet = null
            Toast.makeText(this, "Вы сделали ставку на красное", Toast.LENGTH_SHORT).show()
        }

        blackBetButton.setOnClickListener {
            selectedColorBet = "black"
            selectedBet = null
            Toast.makeText(this, "Вы сделали ставку на черное", Toast.LENGTH_SHORT).show()
        }

        spinButton.setOnClickListener {
            if (selectedBet == null && selectedColorBet == null) {
                Toast.makeText(this, "Выберите ставку!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            degreeOld = degree % 360
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
                        resultMessage.text = ""
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        val result = currentNumber(360 - (degree % 360))
                        resultMessage.text = "Выпало число: $result"

                        val (winningNumber, winningColor) = parseResult(result)

                        when {
                            selectedBet != null && winningNumber == selectedBet -> {
                                showConfetti()
                                Toast.makeText(this@RouletteActivity, "Поздравляем! Вы угадали число!", Toast.LENGTH_SHORT).show()
                            }
                            selectedColorBet != null && winningColor == selectedColorBet -> {
                                showConfetti()
                                Toast.makeText(this@RouletteActivity, "Поздравляем! Вы угадали цвет!", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(this@RouletteActivity, "Увы, вы проиграли. Попробуйте снова!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        selectedBet = null
                        selectedColorBet = null
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}
                })
            }

            ivWheel.startAnimation(rotate)
        }
    }

    private fun setupBetButtons() {
        for (i in 0 until betButtons.childCount) {
            val button = betButtons.getChildAt(i) as? Button
            button?.setOnClickListener {
                val betNumber = button.text.toString().toIntOrNull()
                selectedBet = betNumber
                selectedColorBet = null
                Toast.makeText(this, "Вы выбрали число: $betNumber", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun parseResult(result: String): Pair<Int?, String?> {
        val parts = result.split(" ")
        val number = parts.getOrNull(0)?.toIntOrNull()
        val color = parts.getOrNull(1)
        return number to color
    }

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

    private fun showConfetti() {
        CommonConfetti.rainingConfetti(
            findViewById(R.id.roulette_layout),
            intArrayOf(0xFFE53935.toInt(), 0xFF43A047.toInt(), 0xFF1E88E5.toInt())
        ).stream(3000)
    }
}
