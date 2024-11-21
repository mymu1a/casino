package com.example.casino.blackJack

class Player {
    val hand = mutableListOf<Card>()
    var score: Int = 0
        private set

    fun addCard(card: Card) {
        hand.add(card)
        calculateScore()
    }

    private fun calculateScore() {
        var total = 0
        var aces = 0
        for (card in hand) {
            total += card.value
            if (card.rank == "A") aces++
        }
        while (total > 21 && aces > 0) {
            total -= 10
            aces--
        }
        score = total
    }

    fun reset() {
        hand.clear()
        score = 0
    }
}