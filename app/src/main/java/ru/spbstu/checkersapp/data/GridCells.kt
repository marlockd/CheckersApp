package ru.spbstu.checkersapp.data

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

    fun getTeam(cell: String): Int {
        if (cell !in Grid().gameCells) throw IllegalArgumentException()
        return cells[cell]!!.second.player
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
                if (cell == current.last()) {
                    target = current.size - 3
                    while (target != 0) {
                        if (Move(gridCells, init, env).targetCheck(cell, current[target]) == "emptyMOVE") hover.add(current[target])
                        if (Move(gridCells, init, env).targetCheck(cell, current[target]) == "busyENEMYgo") attack.add(current[target + 1])
                        target--
                    }
                } else if (cell == current.first()) {
                    for (target in 1 until cellIndex) {
                        if (Move(gridCells, init, env).targetCheck(cell, current[target]) == "emptyMOVE") hover.add(current[target])
                        if (Move(gridCells, init, env).targetCheck(cell, current[target]) == "busyENEMYgo") attack.add(current[target + 1])
                    }
                } else {
                    for (target in cellIndex until 0) {
                        if (Move(gridCells, init, env).targetCheck(cell, current[target]) == "emptyMOVE") hover.add(current[target])
                        if (Move(gridCells, init, env).targetCheck(cell, current[target]) == "busyENEMYgo") attack.add(current[target + 1])
                    }
                    for (target in cellIndex until current.size - 1) {
                        if (Move(gridCells, init, env).targetCheck(cell, current[target]) == "emptyMOVE") hover.add(current[target])
                        if (Move(gridCells, init, env).targetCheck(cell, current[target]) == "busyENEMYgo") attack.add(current[target + 1])
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