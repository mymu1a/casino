package com.example.casino.baccarat

data class Card(
    val suit: Suit,  // Масть
    val rank: Rank   // Номинал
)
{
    fun getDrawableName(): String {
        val suitChar = when (suit) {
            Suit.CLUBS -> "c"
            Suit.DIAMONDS -> "d"
            Suit.HEARTS -> "h"
            Suit.SPADES -> "s"
        }
        val rankChar = when (rank) {
            Rank.ACE -> "a"
            Rank.JACK -> "j"
            Rank.QUEEN -> "q"
            Rank.KING -> "k"
            else -> rank.value.toString() // Десятки должны возвращать "10", а не "0"
        }

        val drawableName = "$suitChar$rankChar"
        println("Drawable name for card: $drawableName") // Лог
        return drawableName
    }


}