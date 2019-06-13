package ru.spbstu.checkersapp.data

import android.widget.FrameLayout
import ru.spbstu.checkersapp.R
import ru.spbstu.checkersapp.logic.Env
import ru.spbstu.checkersapp.logic.Init
import ru.spbstu.checkersapp.logic.Move

data class GridCells(val cells: MutableMap<String, Pair<Cell, Figure>>) {

    fun initTable() = cells.forEach { it.value.first.frame.addView(it.value.second.view) }

    fun isEmpty(cell: String): Boolean {
        if (cell !in Grid().gameCells) throw IllegalArgumentException("$cell")
        return cells[cell]!!.second.player == 0
    }
    fun getFrame(cell: String): FrameLayout {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        return cells[cell]!!.first.frame
    }
    fun hoverBy(cell: String): String {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        return cells[cell]!!.first.hoverBy
    }
    fun getFigure(cell: String): Figure {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        return cells[cell]!!.second
    }
    fun getTeam(cell: String): Int {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        return cells[cell]!!.second.player
    }
    fun getCell(cell: String): String {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        return cells[cell]!!.second.cell
    }
    fun isQueen(cell: String): Boolean {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        return cells[cell]!!.second.isQueen
    }

    fun setBg(cell: String, state: String) {
        if (cell !in Grid().gameCells
                || state !in setOf("hover", "attack", "default")) throw IllegalArgumentException()
        when (state)
        {
            "hover" -> cells[cell]!!.first.frame.setBackgroundResource(R.color.rc_grid_square_move)
            "attack" -> cells[cell]!!.first.frame.setBackgroundResource(R.color.rc_grid_square_attack)
            "default" -> cells[cell]!!.first.frame.setBackgroundResource(R.color.rc_grid_square)
        }
    }

    fun availableMoves(cell: String, team: Int, gridCells: GridCells, init: Init, env: Env): Pair<List<String>, List<String>> {
        val verticals = Grid().verticalsCheck(cell)
        val hover = mutableListOf<String>()
        val attack = mutableListOf<String>()
        var current : List<String>


        verticals.forEach {
            current = Grid().getVerticalByName(it.first, it.second)
            if (cell != current.last()) {
                if (isEmpty(current[current.indexOf(cell) + 1])) hover.add(current[current.indexOf(cell) + 1])
                if (!isEmpty(current[current.indexOf(cell) + 1])) {
                    if (Move(gridCells, init, env).targetCheck(gridCells.cells[cell]!!.second,
                                    current[current.indexOf(cell) + 1]) == "busyENEMYgo") {
                        attack.add(current[current.indexOf(cell) + 2])
                    }
                }
            }
        }

        verticals.forEach {
            current = Grid().getVerticalByName(it.first, it.second).reversed()
            if (cell != current.last()) {
                if (isEmpty(current[current.indexOf(cell) + 1])) hover.add(current[current.indexOf(cell) + 1])
                if (!isEmpty(current[current.indexOf(cell) + 1])) {
                    if (Move(gridCells, init, env).targetCheck(gridCells.cells[cell]!!.second,
                                    current[current.indexOf(cell) + 1]) == "busyENEMYgo") {
                        attack.add(current[current.indexOf(cell) + 2])
                    }
                }
            }
        }
        return Pair(hover, attack)
    }

    fun setHover(cell: String, hoverBy: String) {
        cells[cell]!!.first.frame.setBackgroundResource(R.color.rc_grid_square_move)
        cells[cell]!!.first.state = "hover"
        cells[cell]!!.first.hoverBy = hoverBy
    }

    fun setAttack(cell: String, hoverBy: String) {
        cells[cell]!!.first.frame.setBackgroundResource(R.color.rc_grid_square_attack)
        cells[cell]!!.first.state = "attack"
        cells[cell]!!.first.hoverBy = hoverBy
    }

    fun clearHover() {
        cells.forEach {
            it.value.first.state = "default"
            it.value.first.hoverBy = "00"
            it.value.first.frame.setBackgroundResource(R.color.rc_grid_square)
        }
    }

    fun cleanCell(cell: String) {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        cells[cell]!!.first.frame.removeAllViews()
    }

    fun appendCell(cell: String, figure: Figure) {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        cells[cell]!!.first.frame.addView(figure.view)
    }

}