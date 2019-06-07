package ru.spbstu.checkersapp.logic

import android.content.Intent
import android.content.res.Resources

data class Init(var turn: Int, var firstPlayer: String, var secondPlayer: String,
                var scoreFirst: Int, var scoreSecond: Int, var lastMove: Int) {

    fun changeTurn() = when (turn) {
        1 -> turn = 2
        2 -> turn = 1
        else -> throw IllegalStateException("what?")
    }

    fun setNames(intent: Intent) {
        firstPlayer = intent.getStringExtra("player1")
        secondPlayer = intent.getStringExtra("player2")
    }

    fun addPoint(player: Int) {
        when (player) {
            1 -> scoreFirst++
            2 -> scoreSecond++
        }
    }

    fun normalWidth(resources: Resources): Int =
            resources.displayMetrics.widthPixels - (resources.displayMetrics.widthPixels * 0.04).toInt()

    fun cellWidth(resources: Resources): Int =
            ((374 * resources.displayMetrics.density) / 8).toInt()

}