package com.example.casino.baccarat

class GameController {
    private val deck = Deck() // Колода карт
    private val playerHand = Hand("Player")
    private val dealerHand = Hand("Dealer")

    init {
        deck.shuffle()
    }

    // Раздача начальных карт
    fun startGame() {
        playerHand.clearHand()
        dealerHand.clearHand()

        // Раздаем по 2 карты каждому
        repeat(2) {
            playerHand.dealCard(deck.deal())
            dealerHand.dealCard(deck.deal())
        }
    }

    // Добавить карту игроку
    fun dealCardToPlayer() {
        if (playerHand.getCards().size < 3) {
            playerHand.dealCard(deck.deal())
        }
    }

    // Добавить карту дилеру
    fun dealCardToDealer() {
        if (dealerHand.getCards().size < 3) {
            dealerHand.dealCard(deck.deal())
        }
    }

    // Получить карты игрока
    fun getPlayerCards(): List<Card> = playerHand.getCards()

    // Получить карты дилера
    fun getDealerCards(): List<Card> = dealerHand.getCards()

    // Получить количество карт игрока
    fun getPlayerCardCount(): Int = playerHand.getCards().size

    // Получить количество карт дилера
    fun getDealerCardCount(): Int = dealerHand.getCards().size

    // Определение победителя
    fun determineWinner(): String {
        val playerScore = playerHand.getScore()
        val dealerScore = dealerHand.getScore()

        return when {
            playerScore > dealerScore -> "Player wins with $playerScore"
            dealerScore > playerScore -> "Dealer wins with $dealerScore"
            else -> "It's a tie with $playerScore"
        }
    }
}
