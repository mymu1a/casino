package com.example.casino.blackJack

interface GameUI {
    fun updateUI(playerHand: List<Card>, dealerHand: List<Card>, playerScore: Int, dealerScore: Int, showDealerCards: Boolean)
    fun showResult(result: String)
}