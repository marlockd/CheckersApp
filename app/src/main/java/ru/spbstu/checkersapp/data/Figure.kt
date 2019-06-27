package ru.spbstu.checkersapp.data

import android.view.View
import android.widget.ImageView
import ru.spbstu.checkersapp.R

data class Figure(var player: Int, var isQueen: Boolean, var cell: String, var view: ImageView) {

    fun setID(): Figure {
        view.id = View.generateViewId()
        return Figure(player, isQueen, cell, view)
    }

    fun getState(): Pair<String, Int> {
        if (player == 0) return Pair("invisible", 0)
        return when (isQueen) {
            true -> Pair("queen", player)
            false -> Pair("default", player)
        }
    }

    fun setState(state: Pair<String, Int>): Figure {
        if (state.first !in setOf("default", "queen", "invisible")) throw IllegalArgumentException()
        when (state.first) {
            "default" -> {
                !isQueen
                view.visibility = View.VISIBLE
                if (state.second == 1) {
                    player = 1
                    view.setImageResource(R.drawable.rc_figure_blue)
                } else if (state.second == 2) {
                    player = 2
                    view.setImageResource(R.drawable.rc_figure_orange)
                }
            }
            "queen" -> {
                isQueen = true
                view.visibility = View.VISIBLE
                if (state.second == 1) {
                    player = 1
                    view.setImageResource(R.drawable.rc_queen_blue)
                } else if (state.second == 2) {
                    player = 2
                    view.setImageResource(R.drawable.rc_queen_orange)
                }
            }
            "invisible" -> {
                view.visibility = View.INVISIBLE
                player = 0
            }
        }
        return Figure(player, isQueen, cell, view)
    }


}