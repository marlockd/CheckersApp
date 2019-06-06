package ru.spbstu.checkersapp.data

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import ru.spbstu.checkersapp.GameActivity

data class GridCells(val cells: MutableMap<String, Pair<FrameLayout, Figure>>) {

    fun init() = cells.forEach { if (it.value.second.player != 0) it.value.first.addView(it.value.second.view) }

    fun isEmpty(cell: String): Boolean {
        if (cell !in Grid().gameCells) throw IllegalArgumentException("$cell")
        return cells[cell]!!.second.player == 0
    }

    // only when cell is not empty
    fun getTeam(cell: String): Int {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        return cells[cell]!!.second.player
    }

    fun cleanCell(cell: String) {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        cells[cell]!!.first.removeAllViews()
    }

    fun appendCell(cell: String, figure: Figure) {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        cells[cell]!!.first.addView(figure.view)
    }

}