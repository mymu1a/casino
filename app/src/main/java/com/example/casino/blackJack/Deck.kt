package com.example.casino.blackJack

import kotlin.random.Random

class Deck {
    private val suits = listOf("H", "D", "C", "S")
    private val ranks = listOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A")
    private val cards = mutableListOf<Card>()

    init {
        for (suit in suits) {
            for (rank in ranks) {
                cards.add(Card(rank, suit))
            }
        }
        shuffle()
    }

    fun shuffle() {
        cards.shuffle(Random.Default)
    }

    fun drawCard(): Card {
        if (cards.isEmpty()) throw IllegalStateException("Колода пуста!")
        return cards.removeAt(0)
    }
}