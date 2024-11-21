package com.example.casino.slotMachine

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.casino.R
import com.example.casino.imageViewScrolling.IEventEnd
import com.example.casino.imageViewScrolling.ImageViewScrolling
import com.example.casino.Common
import kotlin.random.Random

class SlotMachine : AppCompatActivity(), IEventEnd {

    private lateinit var image: ImageViewScrolling
    private lateinit var image2: ImageViewScrolling
    private lateinit var image3: ImageViewScrolling
    private lateinit var txtScore: TextView
    private lateinit var up: ImageView
    private lateinit var down: ImageView

    private var countDown = 0

    override fun eventEnd(result: Int, count: Int) {
        if (countDown < 2) {
            countDown++  // если еще есть изображение, которое крутится
        } else {
            down.visibility = View.GONE
            up.visibility = View.VISIBLE
            countDown = 0

            // Логика выигрыша
            if (image.value == image2.value && image2.value == image3.value) {
                // Большой выигрыш
                Toast.makeText(this, "You win BIG prize!", Toast.LENGTH_SHORT).show()
                Common.SCORE += 300
                showBigWinAnimation()  // Показать анимацию при большом выигрыше
            } else if (image.value == image2.value || image2.value == image3.value || image.value == image3.value) {
                // Маленький выигрыш
                Toast.makeText(this, "You win small prize", Toast.LENGTH_SHORT).show()
                Common.SCORE += 100
            } else {
                // Проигрыш
                Toast.makeText(this, "You lose", Toast.LENGTH_SHORT).show()
            }

            txtScore.text = Common.SCORE.toString()
        }
    }

    // Метод для отображения анимации при крупном выигрыше
    private fun showBigWinAnimation() {
        val winDialog = WinAnimationDialog()
        winDialog.show(supportFragmentManager, "WinAnimationDialog")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view_scrolling)

        // Инициализация элементов
        image = findViewById(R.id.image)
        image2 = findViewById(R.id.image2)
        image3 = findViewById(R.id.image3)
        txtScore = findViewById(R.id.txt_score)
        up = findViewById(R.id.up)
        down = findViewById(R.id.down)

        // Установка обработчика события окончания
        image.setEventEnd(this)
        image2.setEventEnd(this)
        image3.setEventEnd(this)

        // Обработка нажатия кнопки "Вверх" для начала вращения слотов
        up.setOnClickListener {
            if (Common.SCORE >= 50) {
                up.visibility = View.GONE
                down.visibility = View.VISIBLE

                image.setValueRandom(Random.nextInt(6), Random.nextInt(11) + 5)  // случайное вращение
                image2.setValueRandom(Random.nextInt(6), Random.nextInt(11) + 5)
                image3.setValueRandom(Random.nextInt(6), Random.nextInt(11) + 5)

                Common.SCORE -= 50
                txtScore.text = Common.SCORE.toString()
            } else {
                Toast.makeText(this, "You have not enough money", Toast.LENGTH_SHORT).show()
            }
        }

        // Восстановление состояния после поворота экрана
        savedInstanceState?.let {
            Common.SCORE = it.getInt("score")
            txtScore.text = Common.SCORE.toString()
            if (it.getBoolean("isSpinning")) {
                down.visibility = View.VISIBLE
                up.visibility = View.GONE
            } else {
                down.visibility = View.GONE
                up.visibility = View.VISIBLE
            }
        }
    }

    // Сохранение состояния для счетчика и текущего состояния вращения
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("score", Common.SCORE)
        outState.putBoolean("isSpinning", down.visibility == View.VISIBLE)
    }
}
