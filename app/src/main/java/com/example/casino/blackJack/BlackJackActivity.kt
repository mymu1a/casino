package com.example.casino.blackJack

/*import nl.dionsegijn.konfetti.xml.KonfettiView
import nl.dionsegijn.konfetti.models.Size
import nl.dionsegijn.konfetti.models.Shape*/

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import com.example.casino.R

class BlackJackActivity : AppCompatActivity(), GameUI {

    private lateinit var game: Game
    private lateinit var playerCardViews: List<ImageView>
    private lateinit var dealerCardViews: List<ImageView>
    private lateinit var playerScoreView: TextView
    private lateinit var dealerScoreView: TextView
    private lateinit var resultView: TextView
    private lateinit var hitButton: Button
    private lateinit var standButton: Button
    private lateinit var newGameButton: Button

    private lateinit var deckImage: ImageView
    private lateinit var animatedCard: ImageView // Карта для анимации

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blackjack)

        // Инициализация карт и элементов
        playerCardViews = listOf(
            findViewById(R.id.player_card_1),
            findViewById(R.id.player_card_2),
            findViewById(R.id.player_card_3),
            findViewById(R.id.player_card_4),
            findViewById(R.id.player_card_5)
        )
        dealerCardViews = listOf(
            findViewById(R.id.dealer_card_1),
            findViewById(R.id.dealer_card_2),
            findViewById(R.id.dealer_card_3),
            findViewById(R.id.dealer_card_4),
            findViewById(R.id.dealer_card_5)
        )
        playerScoreView = findViewById(R.id.player_score)
        dealerScoreView = findViewById(R.id.dealer_score)
        resultView = findViewById(R.id.result_text)
        hitButton = findViewById(R.id.hit_button)
        standButton = findViewById(R.id.stand_button)
        newGameButton = findViewById(R.id.new_game_button)

        deckImage = findViewById(R.id.deck_image) // Колода карт
        animatedCard = findViewById(R.id.animated_card) // Карта для анимации

        game = Game(this)

        // Обработчики кнопок
        hitButton.setOnClickListener { game.playerHit() }
        standButton.setOnClickListener { game.playerStand() }
        newGameButton.setOnClickListener {
            resetUI()
            game.startNewGame()
        }

        // Начало первой игры
        game.startNewGame()
    }

    override fun updateUI(playerHand: List<Card>, dealerHand: List<Card>, playerScore: Int, dealerScore: Int, showDealerCards: Boolean) {
        animateCards(playerHand, playerCardViews) // Анимация карт игрока
        if (showDealerCards) {
            animateCards(dealerHand, dealerCardViews) // Анимация карт дилера
        } else {
            updateCardViews(dealerHand.take(1), dealerCardViews) // Показываем только первую карту дилера
        }
        playerScoreView.text = "Очки: $playerScore"
        dealerScoreView.text = if (showDealerCards) "Очки дилера: $dealerScore" else "Очки дилера: ?"
    }

    override fun showResult(result: String) {
        resultView.text = result
        hitButton.isEnabled = true
        standButton.isEnabled = true

/*        if (result.contains("Победа", true)) {
            launchConfetti()
        }*/
    }

/*    private fun launchConfetti() {
        konfettiView.build()
            .addColors(android.graphics.Color.YELLOW, android.graphics.Color.GREEN, android.graphics.Color.MAGENTA)
            .setDirection(0.0, 359.0) // Направление конфетти: 360 градусов
            .setSpeed(1f, 5f) // Скорость конфетти
            .setFadeOutEnabled(true) // Конфетти исчезают
            .setTimeToLive(2000L) // Время жизни конфетти
            .addSizes(Size.SMALL, Size.MEDIUM) // Размеры конфетти
            .setPosition(-50f, konfettiView.width + 50f, -50f, konfettiView.height.toFloat() + 50f)
            .streamFor(300, 3000L) // Количество частиц и длительность
    }*/


    private fun showJackpot() {
        val intent = Intent(this, JackpotActivity::class.java)
        startActivity(intent)
    }

    private fun animateCards(hand: List<Card>, cardViews: List<ImageView>) {
        hand.forEachIndexed { index, card ->
            if (index < cardViews.size) {
                val targetView = cardViews[index]
                animateCard(card, targetView)
            }
        }
    }

    private fun animateCard(card: Card, targetView: ImageView) {
        // Устанавливаем начальное положение анимированной карты
        animatedCard.visibility = View.VISIBLE
        animatedCard.x = deckImage.x
        animatedCard.y = deckImage.y

        // Устанавливаем изображение карты
        val resourceId = resources.getIdentifier(card.getResourceName(), "drawable", packageName)
        animatedCard.setImageResource(resourceId)

        // Создаем анимацию перемещения
        val moveX = ObjectAnimator.ofFloat(animatedCard, "x", targetView.x)
        val moveY = ObjectAnimator.ofFloat(animatedCard, "y", targetView.y)

        // Анимация исчезновения
        val fadeOut = ObjectAnimator.ofFloat(animatedCard, "alpha", 1f, 0f)

        // Объединяем анимации
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(moveX, moveY, fadeOut)
        animatorSet.duration = 1000L // Длительность анимации
        animatorSet.start()

        animatorSet.addListener(onEnd = {
            animatedCard.visibility = View.GONE
            // Устанавливаем конечное изображение на нужное место
            targetView.setImageResource(resourceId)
            targetView.visibility = View.VISIBLE
        })
    }

    private fun updateCardViews(hand: List<Card>, cardViews: List<ImageView>) {
        cardViews.forEachIndexed { index, imageView ->
            if (index < hand.size) {
                val card = hand[index]
                val resourceId = resources.getIdentifier(card.getResourceName(), "drawable", packageName)
                imageView.setImageResource(resourceId) // Устанавливаем изображение карты
                imageView.visibility = View.VISIBLE // Показываем ImageView
            } else {
                imageView.setImageResource(0) // Сбрасываем изображение для пустых слотов
                imageView.visibility = View.INVISIBLE // Скрываем ImageView
            }
        }
    }

    private fun resetUI() {
        // Очистка всех карт игрока
        playerCardViews.forEach { imageView ->
            imageView.setImageResource(0) // Сбрасываем изображение
            imageView.visibility = View.INVISIBLE // Скрываем ImageView
        }

        // Очистка всех карт дилера
        dealerCardViews.forEach { imageView ->
            imageView.setImageResource(0) // Сбрасываем изображение
            imageView.visibility = View.INVISIBLE // Скрываем ImageView
        }

        // Очистка текстовых полей
        playerScoreView.text = "Очки: 0"
        dealerScoreView.text = "Очки дилера: ?"
        resultView.text = ""

        // Переключаем доступность кнопок
        hitButton.isEnabled = true
        standButton.isEnabled = true
    }
}
