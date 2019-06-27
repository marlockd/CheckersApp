package ru.spbstu.checkersapp.logic

import android.content.Intent

data class Init(var turn: Int, var firstPlayer: String, var secondPlayer: String, var isHover: Boolean,
                var scoreFirst: Int, var scoreSecond: Int, var movesList: MutableList<Pair<Int, String>>) {

    fun changeTurn(env: Env) = when (turn) {
        1 -> {
            turn = 2
            env.tbChangePlayer()
        }
        2 -> {
            turn = 1
            env.tbChangePlayer()
        }
        else -> throw IllegalStateException("what?")
    }

    fun setNames(intent: Intent) {
        firstPlayer = intent.getStringExtra("player1")
        secondPlayer = intent.getStringExtra("player2")
    }

    fun addPoint(player: Int, env: Env) {
        if (player == 1) scoreFirst++
        else if (player == 2) scoreSecond++
        env.scoreFirst.text = scoreFirst.toString()
        env.scoreSecond.text = scoreSecond.toString()
    }

    fun isEnd(): Int = when {
        scoreFirst == 12 -> 1
        scoreSecond == 12 -> 2
        else -> 0
    }

}