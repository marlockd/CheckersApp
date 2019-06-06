package ru.spbstu.checkersapp.logic

import android.content.ContentProvider
import android.content.Context
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
import ru.spbstu.checkersapp.data.GridCells
import ru.spbstu.checkersapp.logic.TouchHandler
import kotlin.IllegalArgumentException

class Move constructor(val context: Context) {


    fun isTargetCorrect(figure: Figure, target: String): Boolean {
        if (target !in Grid().gameCells) throw IllegalStateException()
        val verticals = Grid().verticalsCheck(figure.cell)
        if (figure.cell == "a1" || figure.cell == "h8")
            return Grid().getVerticalByName("ALPHA", 1).contains("target")
        if (Grid().getVerticalByName(verticals.first().first, verticals.first().second).contains(target)) return true
        if (Grid().getVerticalByName(verticals.last().first, verticals.last().second).contains(target)) return true
        return false
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
     *  "noneSELF"     -> cell target is the same cell as figure
     */

    fun targetCheck(figure: Figure, target: String, gridCells: GridCells): String {
        var verticle = listOf<String>()

        if (!isTargetCorrect(figure, target)) return "undefCANT"

        val verticals = Grid().verticalsCheck(figure.cell)

        if (figure.cell == "a1" || figure.cell == "h8") verticle = Grid().getVerticalByName("ALPHA", 1)

        if (Grid().getVerticalByName(verticals.first().first, verticals.first().second).contains(target) &&
                Grid().getVerticalByName(verticals.first().first, verticals.first().second).contains(figure.cell))
            verticle = Grid().getVerticalByName(verticals.first().first, verticals.first().second)

        if (Grid().getVerticalByName(verticals.last().first, verticals.last().second).contains(target) &&
                Grid().getVerticalByName(verticals.last().first, verticals.last().second).contains(figure.cell))
            verticle = Grid().getVerticalByName(verticals.last().first, verticals.last().second)
        if (verticle.isNullOrEmpty()) throw IllegalArgumentException()

        val cellIndex = verticle.indexOf(figure.cell)
        val targetIndex = verticle.indexOf(target)

        if (targetIndex > cellIndex) verticle = verticle.reversed()

        if (targetIndex.minus(cellIndex) == 1) {
            if (!gridCells.isEmpty(target)) {
                if (gridCells.getTeam(target) == figure.player) return "busyALLY"
                if (gridCells.getTeam(target) != figure.player &&
                        gridCells.getTeam(target) != 0) {
                    if (!verticle[targetIndex + 1].isNullOrBlank() &&
                            gridCells.isEmpty(verticle[targetIndex + 1])) {
                        if ((verticle[targetIndex + 1][1].toInt() == 8 && figure.player == 1) ||
                                (verticle[targetIndex + 1][1].toInt() == 1 && figure.player == 2))
                            return "busyENEMYqn"
                        else return "busyENEMYgo"
                    }
                } else return "busyENEMY"
            } else return "emptyMOVE"
        } else if (cellIndex.minus(targetIndex) == 1) {
            if (!gridCells.isEmpty(target)) {
                if (gridCells.getTeam(target) == figure.player) return "busyALLY"
                if (gridCells.getTeam(target) != figure.player &&
                        gridCells.getTeam(target) != 0) {
                    if (!verticle[targetIndex + 1].isNullOrBlank() &&
                            gridCells.isEmpty(verticle[targetIndex + 1])) return "busyENEMYgo"
                } else return "busyENEMY"
            } else if (figure.isQueen) return "emptyMOVE" else return "undefCANT"
        } else if (cellIndex == targetIndex) return "noneSELF"
        else return "undefCANT"

        if (figure.isQueen) {
            if (target in verticle && (target != verticle.last() || target != verticle.first())) {
                if (targetIndex.minus(cellIndex) > 2 && gridCells.isEmpty(verticle[targetIndex])) {
                    if (Grid().isEmptyInRange(cellIndex + 1, cellIndex + (targetIndex.minus(cellIndex)),
                                    verticle)) return "emptyMOVE"
                } else if (targetIndex.minus(cellIndex) > 2 && gridCells.isEmpty(verticle[targetIndex + 1])) {
                    if (Grid().isEmptyInRange(cellIndex + 1, cellIndex + (targetIndex - cellIndex + 1),
                                    verticle)) return "busyENEMYgo"
                } else if (cellIndex.minus(targetIndex) > 2 && gridCells.isEmpty(verticle[targetIndex])) {
                    if (Grid().isEmptyInRange(cellIndex + 1, cellIndex + (targetIndex.minus(cellIndex)),
                                    verticle)) return "emptyMOVE"
                } else if (cellIndex.minus(targetIndex) > 2 && gridCells.isEmpty(verticle[targetIndex - 1])) {
                    if (Grid().isEmptyInRange(cellIndex + 1, cellIndex + (targetIndex - cellIndex + 1),
                                    verticle)) return "busyENEMYgo"
                } else if (target in verticle && (target == verticle.last() || target == verticle.first()))
                    return "undefCANT"
            } else return "undefCANT"
        }
        return "ERROR"
    }

    fun moveTo(figure: Figure, target: String, gridCells: GridCells) {
        when (targetCheck(figure, target, gridCells)) {
            "emptyMOVE" -> {
                gridCells.cells[target]!!.second.setState(figure.getState())
                gridCells.cells[figure.cell]!!.second.setState(Pair("invisible", 0))
            }
            "emptyQUEEN" -> {
                gridCells.cells[target]!!.second.setState(Pair("queen", figure.getState().second))
                gridCells.cells[figure.cell]!!.second.setState(Pair("invisible", 0))
            }
            else -> println("Move is restricted by rules. Response state: ${targetCheck(figure, target, gridCells)}")
        }
    }

}


/**

"emptyMOVE"   ->  TODO()
"emptyQUEEN"  ->  TODO()
"undefCANT"   ->  TODO()
"busyALLY"    ->  TODO()
"busyENEMY"   ->  TODO()
"busyENEMYgo" ->  TODO()
"busyENEMYqn" ->  TODO()
"ERROR"       ->  TODO()
"noneSELF"    ->  TODO()

*/