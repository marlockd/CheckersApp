package ru.spbstu.checkersapp.logic

import android.view.MotionEvent
import ru.spbstu.checkersapp.data.Grid
import ru.spbstu.checkersapp.data.GridCells

class TouchHandler(var gridCells: GridCells, var init: Init, var env: Env) {

    fun handleTouch(m: MotionEvent, octa: Int): String {
        val column = when (m.getY(0).toInt()) {
            in 0..octa -> 8
            in octa..(octa * 2) -> 7
            in (octa * 2)..(octa * 3) -> 6
            in (octa * 3)..(octa * 4) -> 5
            in (octa * 4)..(octa * 5) -> 4
            in (octa * 5)..(octa * 6) -> 3
            in (octa * 6)..(octa * 7) -> 2
            in (octa * 7)..(octa * 8) -> 1
            else -> 0
        }
        val grid = when (m.getX(0).toInt()) {
            in 0..octa -> "a"
            in octa..(octa * 2) -> "b"
            in (octa * 2)..(octa * 3) -> "c"
            in (octa * 3)..(octa * 4) -> "d"
            in (octa * 4)..(octa * 5) -> "e"
            in (octa * 5)..(octa * 6) -> "f"
            in (octa * 6)..(octa * 7) -> "g"
            in (octa * 7)..(octa * 8) -> "h"
            else -> "null"
        }
        return if (m.actionMasked == MotionEvent.ACTION_DOWN) {
            listOf(grid, column.toString()).joinToString("")
        } else "ERR"
    }

    fun touchActivityHover(cell: String) {
        if (cell in Grid().gameCells) {
            val availableMoves: Pair<List<String>, List<String>>
            val state = gridCells.cells[cell]!!.first.state
            when (state) {
                "default" -> {
                    if (!gridCells.isEmpty(cell) && gridCells.cells[cell]!!.second.player == init.turn) {
                        availableMoves = gridCells.availableMoves(cell, gridCells, init, env)
                        gridCells.clearHover()
                        if (Grid().isNoAttack(init.turn, gridCells, init, env)) {
                            availableMoves.first.forEach { gridCells.setHover(it, cell) }
                        } else availableMoves.second.forEach { gridCells.setAttack(it, cell) }

                    }
                }
                "hover" -> {
                    init.movesList.add(Pair(gridCells.cells[cell]!!.second.player, buildString {
                        append(gridCells.cells[cell]!!.first.hoverBy).append('-').append(cell)
                    }))
                    Move(gridCells, init, env).moveTo(gridCells.cells[cell]!!.first.hoverBy, cell)
                    gridCells.clearHover()
                }
                "attack" -> {
                    init.movesList.add(Pair(gridCells.cells[cell]!!.second.player, buildString {
                        append(gridCells.cells[cell]!!.first.hoverBy).append(':').append(cell)
                    }))
                    Move(gridCells, init, env).attackTo(gridCells.cells[cell]!!.first.hoverBy, cell)
                    init.addPoint(gridCells.cells[cell]!!.second.player, env)
                    gridCells.clearHover()
                }
            }
        }
    }

}