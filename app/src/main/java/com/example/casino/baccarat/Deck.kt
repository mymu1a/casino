package com.example.casino.baccarat

import kotlin.random.Random

class Deck {
    private val cards: MutableList<Card> = mutableListOf()

    init {
        // Создание колоды из 4-х мастей
        Suit.values().forEach { suit ->
            Rank.values().forEach { rank ->
                cards.add(Card(suit, rank))
            }
        }
    }

    // Перемешивание карт
    fun shuffle() {
        cards.shuffle()
    }

    // Раздача карты
    fun deal(): Card = cards.removeAt(0)

    // Количество карт в колоде
    fun size() = cards.size
}
