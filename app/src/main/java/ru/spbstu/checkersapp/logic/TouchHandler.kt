package ru.spbstu.checkersapp.logic

import android.view.MotionEvent
import ru.spbstu.checkersapp.data.Grid
import ru.spbstu.checkersapp.data.GridCells

class TouchHandler(var gridCells: GridCells, var init: Init) {



    fun handleTouch(m: MotionEvent, octa: Int): String {
        val pointerCount = m.pointerCount

        for (i in 0 until pointerCount)
        {
            val x = m.getX(i)
            val y = m.getY(i)
            val id = m.getPointerId(i)
            val action = m.actionMasked
            val actionIndex = m.actionIndex
            var actionString: String
            var grid = ""

            when (x.toInt())
            {
                in 0..octa -> grid = "a"
                in octa..(octa * 2) -> grid = "b"
                in (octa * 2)..(octa * 3) -> grid = "c"
                in (octa * 3)..(octa * 4) -> grid = "d"
                in (octa * 4)..(octa * 5) -> grid = "e"
                in (octa * 5)..(octa * 6) -> grid = "f"
                in (octa * 6)..(octa * 7) -> grid = "g"
                in (octa * 7)..(octa * 8) -> grid = "h"
                else -> grid = "null"
            }

            var column = 0

            when (y.toInt())
            {
                in 0..octa -> column = 8
                in octa..(octa * 2) -> column = 7
                in (octa * 2)..(octa * 3) -> column = 6
                in (octa * 3)..(octa * 4) -> column = 5
                in (octa * 4)..(octa * 5) -> column = 4
                in (octa * 5)..(octa * 6) -> column = 3
                in (octa * 6)..(octa * 7) -> column = 2
                in (octa * 7)..(octa * 8) -> column = 1
                else -> column = 1
            }

            if (id == 0 && action == MotionEvent.ACTION_DOWN) {
                return listOf(grid, column.toString()).joinToString("")
            }
            else
                return "ERR"
        }
        throw IllegalStateException()
    }

    fun touchActivityHover(cell: String) {
        val availableMoves: Pair<List<String>, List<String>>
        if (cell in Grid().gameCells) {
            val state = gridCells.cells[cell]!!.first.state
            when (state) {
                "default" -> {
                    if (!gridCells.isEmpty(cell) && gridCells.cells[cell]!!.second.player == init.turn) {
                        availableMoves = gridCells.availableMoves(cell, init.turn, gridCells, init)
                        availableMoves.first.forEach { gridCells.setHover(it, cell) }
                        availableMoves.second.forEach { gridCells.setAttack(it, cell) }
                    }
                }
                "hover" -> {
                    println(gridCells.cells[cell]!!.first.hoverBy)
                    println(cell)
                    Move(gridCells, init).moveTo(gridCells.cells[cell]!!.first.hoverBy, cell)
                    gridCells.clearHover()
                }
                "attack" -> {
                    println(gridCells.cells[cell]!!.first.hoverBy)
                    println(cell)
                    Move(gridCells, init).attackTo(gridCells.cells[cell]!!.first.hoverBy, cell)
                    gridCells.clearHover()
                }
            }
        }
    }

}