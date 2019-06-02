package ru.spbstu.checkersapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game_mode.*

class GameMode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_mode)
        bottom_nav_bar.setIconSize(29f, 29f)
        bottom_nav_bar.setTextVisibility(false)
        bottom_nav_bar.enableItemShiftingMode(false)
        bottom_nav_bar.enableShiftingMode(false)
        bottom_nav_bar.enableAnimation(false)
        for(i in 0 until bottom_nav_bar.menu.size()) {
            bottom_nav_bar.setIconTintList(i, null)
        }
    }
}
