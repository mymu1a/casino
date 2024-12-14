package com.example.casino.baccarat

class Hand(val name: String) {
    private val cards: MutableList<Card> = mutableListOf()

    // Раздача карты в руку
    fun dealCard(card: Card) {
        cards.add(card)
    }

    // Подсчёт очков (с учётом логики Baccarat)
    fun getScore(): Int {
        var score = cards.sumOf { it.rank.value }
        return score % 10 // Сумма по модулю 10
    }

    fun clearHand() {
        cards.clear()
    }

    fun getCards(): List<Card> = cards
}
