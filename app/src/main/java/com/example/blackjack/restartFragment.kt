package com.example.blackjack

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.rate_fragment.*
import kotlinx.android.synthetic.main.restart_fragment.*

class restartFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // return super.onCreateView(inflater, container, savedInstanceState)
        val rView = inflater.inflate(R.layout.restart_fragment, container, false)
        rView.background = rView.resources.getDrawable(R.drawable.dialog_background)


        return rView

    }
}