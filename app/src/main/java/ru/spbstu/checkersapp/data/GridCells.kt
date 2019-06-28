package ru.spbstu.checkersapp.data

import ru.spbstu.checkersapp.R
import ru.spbstu.checkersapp.logic.Env
import ru.spbstu.checkersapp.logic.Init
import ru.spbstu.checkersapp.logic.Move

data class GridCells(val cells: MutableMap<String, Pair<Cell, Figure>>) {

    fun initTable() = cells.forEach { it.value.first.frame.addView(it.value.second.view) }

    fun isEmpty(cell: String): Boolean {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        return cells[cell]!!.second.player == 0
    }

    fun getTeam(cell: String): Int {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        return cells[cell]!!.second.player
    }

    fun emptyInRange(startIndex: Int, endIndex: Int, vertical: List<String>): Boolean {
        val cellRange = mutableListOf<String>()
        for (i in startIndex + 1 until endIndex + 1) cellRange.add(vertical[i])
        cellRange.forEach { if (!isEmpty(it)) return false }
        return true
    }

    fun availableMoves(cell: String, gridCells: GridCells, init: Init, env: Env): Pair<List<String>, List<String>> {
        val verticals = Grid().verticalsCheck(cell)
        val hover = mutableListOf<String>()
        val attack = mutableListOf<String>()
        var current: List<String>

        if (!gridCells.cells[cell]!!.second.isQueen) {
            verticals.forEach {
                current = Grid().getVerticalByName(it.first, it.second)
                if (getTeam(cell) == 2) current = current.reversed()
                if (cell != current.last()) {
                    if (isEmpty(current[current.indexOf(cell) + 1])) hover.add(current[current.indexOf(cell) + 1])
                    if (!isEmpty(current[current.indexOf(cell) + 1])) {
                        if (Move(gridCells, init, env).targetCheck(cell,
                                        current[current.indexOf(cell) + 1]) == "busyENEMYgo") {
                            attack.add(current[current.indexOf(cell) + 2])
                        }
                    }
                }
            }
            verticals.forEach {
                current = Grid().getVerticalByName(it.first, it.second)
                if (getTeam(cell) == 1) current = current.reversed()
                if (cell != current.last()) {

                    if (!isEmpty(current[current.indexOf(cell) + 1])) {
                        if (Move(gridCells, init, env).targetCheck(cell,
                                        current[current.indexOf(cell) + 1]) == "busyENEMYgo") {
                            attack.add(current[current.indexOf(cell) + 2])
                        }
                    }
                }
            }
        } else if (gridCells.cells[cell]!!.second.isQueen) {
            verticals.forEach {
                current = Grid().getVerticalByName(it.first, it.second)
                val cellIndex = current.indexOf(cell)
                var target = 0
                var checkState = ""
                current.forEach {
                    val rs = it.takeLast(1).toInt() - cell.takeLast(1).toInt()
                    checkState = Move(gridCells, init, env).targetCheck(cell, it)
                    when (checkState) {
                        "emptyMOVE" -> if (it != cell) hover.add(it)
                        "busyENEMYgo" -> {
                            if (it != cell && rs < 0) attack.add(current[current.indexOf(it) - 1])
                            else if (it != cell && rs > 0) attack.add(current[current.indexOf(it) + 1])
                        }
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

}