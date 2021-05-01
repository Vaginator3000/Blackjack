package com.example.blackjack

import android.content.Context
import android.content.res.AssetManager
import android.widget.Toast
import java.lang.Exception
import java.util.ArrayList

class DeckBuilder {
    fun makeDeck(context: Context): ArrayList<Int> {
        val deck = ArrayList<Int>()
        val cards = R.drawable::class.java.declaredFields

        for (i in 0 until cards.size) {
            try {
                val id = cards[i].getInt(context.assets)
                val name = context.resources.getResourceEntryName(id)

                if (name.matches(Regex("(ace|two|three|four|five|six|seven|eight|nine|ten|jack|queen|king).*")))
                    deck.add(id)
            }
            catch (e: Exception) {
                e.localizedMessage
                Toast.makeText(context,e.toString(), Toast.LENGTH_LONG).show()
                continue
            }
        }

        return deck
    }
}