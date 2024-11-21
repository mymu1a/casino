package com.example.casino.blackJack

class Game(private val gameUI: GameUI) {
    private val deck = Deck()
    private val playerHand = mutableListOf<Card>()
    private val dealerHand = mutableListOf<Card>()
    private var gameOver = false

    fun startNewGame() {
        playerHand.clear()
        dealerHand.clear()
        gameOver = false
        deck.shuffle()

        // Раздача начальных карт
        playerHand.add(deck.drawCard())
        playerHand.add(deck.drawCard())
        dealerHand.add(deck.drawCard())
        dealerHand.add(deck.drawCard())

        // Обновление интерфейса
        updateUI(showDealerCards = false)
        gameUI.showResult("") // Убираем текст результата
    }

    fun playerHit() {
        if (gameOver) return

        // Игрок берет карту
        playerHand.add(deck.drawCard())
        updateUI(showDealerCards = false)

        // Проверка на превышение 21
        if (calculateScore(playerHand) > 21) {
            gameOver = true
            gameUI.showResult("Перебор")
        }
    }

    fun playerStand() {
        if (gameOver) return

        // Открываем карты дилера
        while (calculateScore(dealerHand) < 17) {
            dealerHand.add(deck.drawCard())
        }

        // Вычисление результата
        val playerScore = calculateScore(playerHand)
        val dealerScore = calculateScore(dealerHand)
        gameOver = true

        val result = when {
            dealerScore > 21 || playerScore > dealerScore -> "Победа"
            dealerScore == playerScore -> "Ничья"
            else -> "Попробуйте еще раз"
        }
        gameUI.showResult(result)
        updateUI(showDealerCards = true)
    }

    private fun calculateScore(hand: List<Card>): Int {
        var score = hand.sumOf { it.value }
        var aceCount = hand.count { it.rank == "A" }

        // Корректировка очков для тузов
        while (score > 21 && aceCount > 0) {
            score -= 10
            aceCount--
        }

        return score
    }

    private fun updateUI(showDealerCards: Boolean) {
        gameUI.updateUI(
            playerHand = playerHand,
            dealerHand = dealerHand,
            playerScore = calculateScore(playerHand),
            dealerScore = calculateScore(dealerHand),
            showDealerCards = showDealerCards
        )
    }
}
