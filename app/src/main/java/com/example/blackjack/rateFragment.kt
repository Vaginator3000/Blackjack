package com.example.blackjack

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.rate_fragment.*

class rateFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       // return super.onCreateView(inflater, container, savedInstanceState)
        val rView = inflater.inflate(R.layout.rate_fragment, container, false)
        rView.background = rView.resources.getDrawable(R.drawable.dialog_background)


        val sBar : SeekBar = rView.findViewById(R.id.rateValue)
        sBar.progress = 10
        sBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (rateValue.progress < 10) rateValue.progress = 10 //for small API
                var text = getString(R.string.rateText)
                tvRate.text = String.format(text, rateValue.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })


        return rView

    }
}