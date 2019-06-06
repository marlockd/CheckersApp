package ru.spbstu.checkersapp.logic

import android.provider.Settings.Global.getString
import ru.spbstu.checkersapp.R
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.counter_score_names.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.game_grid.*
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import kotlinx.android.synthetic.main.counter_time_moves.*
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.*
import ru.spbstu.checkersapp.GameActivity
import ru.spbstu.checkersapp.data.Figure
import ru.spbstu.checkersapp.data.Grid
import ru.spbstu.checkersapp.logic.TouchHandler
import kotlin.IllegalArgumentException

class Move {

    fun isTargetCorrect(figure: Figure, target: String): Boolean {
        if (target !in Grid().gameCells) throw IllegalStateException()
        val verticals = Grid().verticalsCheck(figure.cell)
        if (verticals.size == 1) if (target !in Grid().getVerticalByName(
                        verticals.first().first, verticals.first().second)) return false

        if (verticals.size == 2) if ((target !in Grid().getVerticalByName(
                        verticals.first().first, verticals.first().second)) &&
                (target !in Grid().getVerticalByName(
                        verticals.first().first, verticals.first().second))) return false
        return true
    }

    /** STATES LIST:
     *  "emptyMOVE"    -> figure can move to this cell
     *  "emptyQUEEN"   -> figure is empty and ready to upgrade figure to Queen
     *  "undefCANT"    -> cell is placed in unreachable vertical
     *  "busyALLY"     -> cell is busy by ally figure, no actions provided
     *  "busyENEMY"    -> cell is busy by enemy figure, no actions provided
     *  "busyENEMYgo"  -> cell is busy by enemy figure, attack is possible here
     *  "busyENEMYqn"  -> cell is busy by enemy figure, attack is possible here and ready to upgrade figure to Queen
     *  "ERROR"        -> requested cell is not existing or not included in rule set
     */

    /**
    fun targetCheck(figure: Figure, target: String): String {
        var verticle = listOf<String>()
        val state = mutableListOf("ER", "ROR")

        if (!isTargetCorrect(figure, target)) return "undefCANT"

        Grid().verticalsCheck(figure.cell).forEach {
            val temp = Grid().getVerticalByName(it.first, it.second)
            if (temp.contains(target) && temp.contains(figure.cell)) verticle = temp
        }
        if (verticle.isNullOrEmpty()) throw IllegalArgumentException()

        val cellIndex = verticle.indexOf(figure.cell)
        val targetIndex = verticle.indexOf(target)

        when (figure.isQueen) {

            false ->
            {
                if (targetIndex - cellIndex == 1) {
                    if (GameActivity().cellById(verticle[targetIndex]).childCount == 0) return "emptyMOVE"
                    if (GameActivity().cellById(verticle[targetIndex]).childCount == 1) {
                        if (GameActivity().cellById(verticle[targetIndex]).get)
                    }
                }
            }

            true ->
            {

            }
        }

    }

    fun moveTo(figure: Figure, target: String) {
        if (!isTargetCorrect(figure, target)) throw IllegalArgumentException()

        val verticals = Grid().verticalsCheck(figure.cell)


        when (figure.isQueen)
        {
            false -> {

            }

            true -> {

            }
        }


                if (target !in Grid().getVerticalByName(it.first, it.second))

        if (GameActivity().cellById(target).childCount == 0)
    }
*/
}