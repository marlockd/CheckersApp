package ru.spbstu.checkersapp.logic

import android.view.MotionEvent
import java.lang.IllegalStateException

public class TouchHandler {

    public fun handleTouch(m: MotionEvent, octa: Int): String {
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

            when (action)
            {
                MotionEvent.ACTION_DOWN -> actionString = "DOWN"
                MotionEvent.ACTION_UP -> actionString = "UP"
                MotionEvent.ACTION_POINTER_DOWN -> actionString = "PNTR DOWN"
                MotionEvent.ACTION_POINTER_UP -> actionString = "PNTR UP"
                MotionEvent.ACTION_MOVE -> actionString = "MOVE"
                else -> actionString = ""
            }

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

            val touchStatus =
                    "Action: $actionString Index: $actionIndex ID: $id X: $x Y: $y"

            if (id == 0) {
                return listOf(grid, column.toString()).joinToString("")
            }
            else
                return "ERR"
        }
        throw IllegalStateException()
    }

}