package com.example.blackjack

import android.content.Context
import android.content.SharedPreferences
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.rate_fragment.*
import java.lang.Exception
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var pref : SharedPreferences? = null
    private var cash = 1000

    private var pCards = ArrayList<ImageView>()
    private var dCards = ArrayList<ImageView>()
    private var deckBuilder = DeckBuilder()
    private var player = Player()
    private var dealer = Player()
    lateinit var cards: ArrayList<Int>

    var amountPlayerCards = 0
    var amountDealerCards = 0
    var idFirstCard = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment2.view!!.visibility = View.GONE //don`t show restartFragment

        // get cash and set it to textview
        pref = getSharedPreferences("DATA", Context.MODE_PRIVATE)
        cash = pref?.getInt("cash", 1000)!!

        setCashTv(0)

        activateBtn(0)

        initCards()

        btnHit.setOnClickListener {
            val r = Random.nextInt(cards.size)
            val cardId = cards[r]
            cards.remove(r)

            val cardName = resources.getResourceEntryName(cardId)
            player.addCard(cardName)

            pCards[amountPlayerCards].setImageResource(cardId)
            amountPlayerCards++

            val cardValue = getCardValue(cardName)
            val score = player.getScore(cardValue)
            tvPlayerScore.text = score.toString()

            if (score == 21)
                btnPass.callOnClick()
            if (score > 21 && dealer.getScore(0) <= 21)
                btnPass.callOnClick()
        }

        btnPass.setOnClickListener {
            while (dealer.getScore(0) < 17) {
                val cardId = cards[Random.nextInt(cards.size)]
                cards.remove(cardId)

                val cardName = resources.getResourceEntryName(cardId)
                dealer.addCard(cardName)

                dCards[amountDealerCards].setImageResource(cardId)
                amountDealerCards++

                val cardValue = getCardValue(cardName)
                dealer.getScore(cardValue)
            }
            dCards[0].setImageResource(idFirstCard)

            tvDealerScore.text = dealer.getScore(0).toString()
            tvDealerScore.visibility = View.VISIBLE

            setWinner()
        }
    }

    private fun setWinner() {
        val pScore = player.getScore(0)
        val dScore = dealer.getScore(0)

        if(pScore == 21 || pScore > dScore && pScore < 21 || pScore <= 21 && dScore > 21) {
            setCashTv(2 * rateValue.progress)
            Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show()
            tvWinner.text = "You win!"
        } else if (dScore == 21 || pScore < dScore || dScore <= 21 && pScore > 21) {
            Toast.makeText(this, "You lose!", Toast.LENGTH_SHORT).show()
            tvWinner.text = "You lose!"
        } else if (dScore == pScore || dScore > 21 && pScore > 21)  {
            setCashTv(rateValue.progress)
            Toast.makeText(this, "No one won!", Toast.LENGTH_SHORT).show()
            tvWinner.text = "No one won!"
        }

        activateBtn(0)
        playAgain()

    }

    private fun setCashTv(addValue: Int) {
        cash += addValue
        var text = getString(R.string.cashText)
        tvCash.text = String.format(text, cash)
        pref?.edit()?.putInt("cash", cash)?.apply()

    }

    private fun playAgain() {
        fragment2.view!!.visibility = View.VISIBLE

    }

    private fun initCards() {
        cards = deckBuilder.makeDeck(this)
        pCards = arrayListOf(pCard1,pCard2, pCard3, pCard4, pCard5, pCard6, pCard7, pCard8, pCard9, pCard10)
        dCards = arrayListOf(dCard1,dCard2, dCard3, dCard4, dCard5, dCard6, dCard7, dCard8, dCard9, dCard10)

        var isFirstCard = true
        var cardId = 0

        for (i in 0 until 2) {
            cardId = cards[Random.nextInt(cards.size)]
            var cardName: String = "name"
            cardName = resources.getResourceEntryName(cardId)

            //dealer`s turn
            if (i % 2 == 1) {
                dealer.addCard(cardName)

                if(isFirstCard) {
                    idFirstCard = cardId
                    isFirstCard = false
                }

                val cardValue = getCardValue(cardName)
                dealer.getScore(cardValue)

                if (amountDealerCards == 0) {
                    dCards[amountDealerCards].setImageResource(R.drawable.back2)
                    idFirstCard = cardId
                } else {
                    dCards[amountDealerCards].setImageResource(cardId)
                }
                amountDealerCards++

            } else {
                player.addCard(cardName)
                val cardValue = getCardValue(cardName)
                tvPlayerScore.text = player.getScore(cardValue).toString()
                try {
                    pCards[amountPlayerCards].setImageResource(cardId)
                } catch (e: Exception) {
                    Log.d("MyLog", "initCards() + ${e.toString()}")
                    Toast.makeText(this, "initCards() +  ${e.toString()}", Toast.LENGTH_LONG).show()
                }
                amountPlayerCards++
            }
            cards.remove(cardId)
        }
    }

    private fun activateBtn(key: Int) {
        if (key == 1) {
            btnHit.isEnabled = true
            btnPass.isEnabled = true
        } else {
            btnHit.isEnabled = false
            btnPass.isEnabled = false
        }
    }

    private fun getCardValue(card: String): Int  {
        when(card) {
            "ace_of_clubs", "ace_of_diamonds", "ace_of_hearts","ace_of_spades" -> return 1
            "two_of_clubs", "two_of_diamonds", "two_of_hearts","two_of_spades" -> return 2
            "three_of_clubs", "three_of_diamonds", "three_of_hearts","three_of_spades" -> return 3
            "four_of_clubs", "four_of_diamonds", "four_of_hearts","four_of_spades" -> return 4
            "five_of_clubs", "five_of_diamonds", "five_of_hearts","five_of_spades" -> return 5
            "six_of_clubs", "six_of_diamonds", "six_of_hearts","six_of_spades" -> return 6
            "seven_of_clubs", "seven_of_diamonds", "seven_of_hearts","seven_of_spades" -> return 7
            "eight_of_clubs", "eight_of_diamonds", "eight_of_hearts", "eight_of_spades" -> return 8
            "nine_of_clubs", "nine_of_diamonds", "nine_of_hearts", "nine_of_spades" -> return 9
            "ten_of_clubs", "ten_of_diamonds", "ten_of_hearts", "ten_of_spades"  -> return 10

            "jack_of_clubs", "jack_of_diamonds", "jack_of_hearts", "jack_of_spades"  -> return 10
            "queen_of_clubs", "queen_of_diamonds", "queen_of_hearts", "queen_of_spades"  -> return 10
            "king_of_clubs", "king_of_diamonds", "king_of_hearts", "king_of_spades"  -> return 10
            else -> return 99999999
        }
    }

    // START btn click
    fun onStartGame(view: View) {

        if (rateValue.progress > cash) {
            Toast.makeText(this, "No cash!", Toast.LENGTH_SHORT).show()
        } else {
            logo.visibility = View.GONE
            fragment.view!!.visibility = View.GONE
            activateBtn(1)
            setCashTv(-rateValue.progress)
        }
    }

    fun onClicBtnNo(view: View) {
        finish()
    }

    fun onClicBtnYes(view: View) {
        this.recreate()
    }

}