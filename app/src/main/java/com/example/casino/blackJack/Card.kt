package com.example.casino.blackJack

data class Card(
    val rank: String,
    val suit: String
) {
    val value: Int
        get() = when (rank) {
            "A" -> 11
            "K", "Q", "J" -> 10
            else -> rank.toInt()
        }

    /**
     * Генерация имени ресурса карты на основе масти и ранга.
     * suit: "H" (черви), "D" (бубны), "C" (трефы), "S" (пики)
     * rank: "2", "3", ..., "10", "J", "Q", "K", "A"
     * Пример: d2, s10
     */
    fun getResourceName(): String {
        val suitPrefix = when (suit) {
            "H" -> "h" // Черви
            "D" -> "d" // Бубны
            "C" -> "c" // Трефы
            "S" -> "s" // Пики
            else -> throw IllegalArgumentException("Unknown suit: $suit")
        }
        return "$suitPrefix$rank".lowercase()
    }
}
