package com.example.casino.baccarat

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.casino.R
import com.github.jinatonic.confetti.CommonConfetti

class BaccaratActivity : ComponentActivity() {

    private lateinit var gameController: GameController
    private lateinit var dealerCardsContainer: LinearLayout
    private lateinit var playerCardsContainer: LinearLayout
    private lateinit var deckImage: ImageView
    private lateinit var scoreTextView: TextView
    private lateinit var drawCardButton: Button
    private lateinit var takeCardButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baccarat)

        // Инициализация компонентов
        dealerCardsContainer = findViewById(R.id.dealerCardsContainer)
        playerCardsContainer = findViewById(R.id.playerCardsContainer)
        deckImage = findViewById(R.id.deckImageView)
        scoreTextView = findViewById(R.id.scoreText)
        drawCardButton = findViewById(R.id.drawCardButton)
        takeCardButton = findViewById(R.id.takeCardButton)

        gameController = GameController()

        // Начало игры
        findViewById<Button>(R.id.startGameButton).setOnClickListener {
            gameController.startGame()
            updateUI()
            drawCardButton.isEnabled = true
            takeCardButton.isEnabled = true
        }

        // Логика для "Take Card"
        takeCardButton.setOnClickListener {
            if (gameController.getPlayerCardCount() < 3) {
                gameController.dealCardToPlayer()
                updateUI()
            }
        }

        // Логика для "Draw Cards"
        drawCardButton.setOnClickListener {
            if (gameController.getDealerCardCount() < 3) {
                gameController.dealCardToDealer()
                updateUI()
            }

            if (gameController.getDealerCardCount() == 3) {
                val winner = gameController.determineWinner()
                scoreTextView.text = winner
                drawCardButton.isEnabled = false
                takeCardButton.isEnabled = false

                // Показываем конфетти, если игрок выиграл
                if (winner.startsWith("Player")) {
                    showConfetti()
                }
            }
        }
    }

    // Обновление UI
    private fun updateUI() {
        // Обновляем карты игрока и дилера
        updateCardsDisplay(gameController.getPlayerCards(), playerCardsContainer)
        updateCardsDisplay(gameController.getDealerCards(), dealerCardsContainer)
    }

    // Метод для отображения карт в контейнере
    private fun updateCardsDisplay(cards: List<Card>, container: LinearLayout) {
        container.removeAllViews() // Очистить контейнер перед обновлением

        for (card in cards) {
            val imageView = ImageView(this)
            val drawableName = card.getDrawableName()
            val resId = resources.getIdentifier(drawableName, "drawable", packageName)

            if (resId != 0) { // Проверяем, что ресурс существует
                imageView.setImageResource(resId)
            } else {
                imageView.setImageResource(R.drawable.gray_back) // Фон, если ресурс не найден
            }

            // Настраиваем размеры и отступы для каждой карты
            val params = LinearLayout.LayoutParams(300, 350) // Ширина и высота изображения
            params.setMargins(8, 0, 8, 0)
            imageView.layoutParams = params

            container.addView(imageView) // Добавляем карту в контейнер
        }
    }

    // Метод для показа конфетти
    private fun showConfetti() {
        CommonConfetti.rainingConfetti(
            findViewById(R.id.playerCardsContainer), // Используйте контейнер игрока для конфетти
            intArrayOf(0xFFE53935.toInt(), 0xFF43A047.toInt(), 0xFF1E88E5.toInt())
        ).stream(3000)
    }

    // Получаем ID ресурса для карты
    private fun getCardResourceId(card: Card): Int {
        val cardName = card.suit.name.first().lowercaseChar() + card.rank.value.toString()
        return resources.getIdentifier(cardName, "drawable", packageName)
    }
}