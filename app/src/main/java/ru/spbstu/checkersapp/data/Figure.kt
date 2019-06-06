package ru.spbstu.checkersapp.data

import android.view.View
import android.widget.ImageView
import ru.spbstu.checkersapp.R

data class Figure(var player: Int, var isQueen: Boolean, var cell: String, var view: ImageView) {

    fun setID():Figure {
        view.id = View.generateViewId()
        return Figure(player, isQueen, cell, view)
    }

    fun setState(state: String): Figure {
        if (state !in setOf("default", "queen", "invisible")) throw IllegalArgumentException()
        when (state)
        {
            "default" -> if (player == 1) view.setImageResource(R.drawable.rc_figure_blue) else
                if (player == 2) view.setImageResource(R.drawable.rc_figure_orange)
            "queen" -> if (player == 1) view.setImageResource(R.drawable.rc_queen_blue) else
                if (player == 2) view.setImageResource(R.drawable.rc_queen_orange)
            "invisible" -> {
                view.visibility = View.INVISIBLE
                player = 0
            }
        }
        return Figure(player, isQueen, cell, view)
    }

    // temporary solution, I know, that this looks very funny
    fun becomeTo(other: Figure):Figure = other

}