package ru.spbstu.checkersapp.data

import android.content.Context
import android.widget.ImageView
import ru.spbstu.checkersapp.GameActivity
import ru.spbstu.checkersapp.R
import ru.spbstu.checkersapp.logic.Move

data class GridCells(val cells: MutableMap<String, Pair<Cell, Figure>>) {

    fun init(context: Context) {
        for (i in 0 until Grid().gameCells.size) when (i) {
            in 0..11 -> cells[Grid().gameCells[i]] = Pair(Cell(GameActivity().cellById(Grid().gameCells[i]), "default", "00"), Figure(2,
                    false, Grid().gameCells[i], ImageView(context)).setID().setState(Pair("default", 2)))

            in 12..19 -> cells[Grid().gameCells[i]] = Pair(Cell(GameActivity().cellById(Grid().gameCells[i]), "default", "00"), Figure(0,
                    false, Grid().gameCells[i], ImageView(context)).setID().setState(Pair("invisible", 0)))

            in 20..31 -> cells[Grid().gameCells[i]] = Pair(Cell(GameActivity().cellById(Grid().gameCells[i]), "default", "00"), Figure(1,
                    false, Grid().gameCells[i], ImageView(context)).setID().setState(Pair("default", 1)))
        }
    }

    fun initTable() = cells.forEach { it.value.first.frame.addView(it.value.second.view) }

    fun isEmpty(cell: String): Boolean {
        if (cell !in Grid().gameCells) throw IllegalArgumentException("$cell")
        return cells[cell]!!.second.player == 0
    }

    // only when cell is not empty
    fun getTeam(cell: String): Int {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        return cells[cell]!!.second.player
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

    fun availableMoves(cell: String, team: Int, gridCells: GridCells): Pair<List<String>, List<String>> {
        val verticals = Grid().verticalsCheck(cell)
        val hover = mutableListOf<String>()
        val attack = mutableListOf<String>()
        var current : List<String>

        verticals.forEach {
            current = Grid().getVerticalByName(it.first, it.second)
            if (team == 2) current = current.reversed()
            if (cell != current.last()) {
                if (isEmpty(current[current.indexOf(cell) + 1])) hover.add(current[current.indexOf(cell) + 1])
                if (!isEmpty(current[current.indexOf(cell) + 1])) {
                    if (Move().targetCheck(gridCells.cells[cell]!!.second,
                                    current[current.indexOf(cell) + 1], gridCells) == "busyENEMYgo") {
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