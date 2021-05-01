package com.example.blackjack

class Player {
    private var score = 0
    private var cards = ArrayList<String>()

    fun getScore(cardValue: Int): Int {
        score += cardValue
        return score
    }

    fun addCard (card: String) {
        cards.add(card)
    }
}