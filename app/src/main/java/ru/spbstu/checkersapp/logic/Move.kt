package ru.spbstu.checkersapp.logic

import ru.spbstu.checkersapp.data.Figure
import ru.spbstu.checkersapp.data.Grid
import ru.spbstu.checkersapp.data.GridCells

class Move(var gridCells: GridCells, var init: Init, var env: Env) {


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

    fun targetCheck(cell: String, target: String): String {
        if (cell !in Grid().gameCells) throw IllegalStateException()
        val figure = gridCells.cells[cell]!!.second
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

        if (figure.player == 2) verticle = verticle.asReversed()

        val cellIndex = verticle.indexOf(figure.cell)
        val targetIndex = verticle.indexOf(target)

        if (targetIndex.minus(cellIndex) == 1) {
            if (!gridCells.isEmpty(target)) {
                if (gridCells.getTeam(target) == figure.player) return "busyALLY"
                if (gridCells.getTeam(target) != figure.player &&
                        gridCells.getTeam(target) != 0) {

                    if (((targetIndex + 1) in 0..(verticle.size - 1)) && gridCells.isEmpty(verticle[targetIndex + 1])) {

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
                    if (!verticle[targetIndex + 1].isBlank() &&
                            gridCells.isEmpty(verticle[targetIndex + 1])) return "busyENEMYgo"
                } else return "busyENEMY"
            } else if (figure.isQueen) return "emptyMOVE" else return "undefCANT"
        } else if (cellIndex == targetIndex) return "noneSELF"
        else return "undefCANT"

        if (figure.isQueen) {
            if (target in verticle && (target != verticle.last() || target != verticle.first())) {
                if (targetIndex.minus(cellIndex) > 2 && gridCells.isEmpty(verticle[targetIndex])) {
                    if (Grid().isEmptyInRange(cellIndex + 1, cellIndex + (targetIndex.minus(cellIndex)),
                                    verticle, gridCells)) return "emptyMOVE"
                } else if (targetIndex.minus(cellIndex) > 2 && gridCells.isEmpty(verticle[targetIndex + 1])) {
                    if (Grid().isEmptyInRange(cellIndex + 1, cellIndex + (targetIndex - cellIndex + 1),
                                    verticle, gridCells)) return "busyENEMYgo"
                } else if (cellIndex.minus(targetIndex) > 2 && gridCells.isEmpty(verticle[targetIndex])) {
                    if (Grid().isEmptyInRange(cellIndex + 1, cellIndex + (targetIndex.minus(cellIndex)),
                                    verticle, gridCells)) return "emptyMOVE"
                } else if (cellIndex.minus(targetIndex) > 2 && gridCells.isEmpty(verticle[targetIndex - 1])) {
                    if (Grid().isEmptyInRange(cellIndex + 1, cellIndex + (targetIndex - cellIndex + 1),
                                    verticle, gridCells)) return "busyENEMYgo"
                } else if (target in verticle && (target == verticle.last() || target == verticle.first()))
                    return "undefCANT"
            } else return "undefCANT"
        }
        return "ERROR"
    }

    fun isQueen(cell: String) {
        val team = gridCells.cells[cell]!!.second.player
        if  (((team == 1) && (cell.takeLast(1).toInt() == 8)) || ((team == 2) && (cell.takeLast(1).toInt() == 1)))
            gridCells.cells[cell]!!.second.setState(Pair("queen", team))
    }

    fun moveTo(cell: String, target: String) {
        when {
            targetCheck(cell, target) == "emptyMOVE" ->
                gridCells.cells[target]!!.second.setState(gridCells.cells[cell]!!.second.getState())

            targetCheck(cell, target) == "emptyQUEEN" ->
                gridCells.cells[target]!!.second.setState(Pair("queen", gridCells.cells[cell]!!.second.getState().second))

            else ->
                println("Move is restricted by rules. Response state: ${targetCheck(cell, target)}")
        }
        gridCells.cells[gridCells.cells[cell]!!.second.cell]!!.second.setState(Pair("invisible", 0))
        isQueen(cell)
        env.updateMoves(init)
        init.changeTurn(env)
    }

    fun attackTo(cell: String, target: String) {
        val verticals = Grid().verticalsCheck(cell)
        var verticle = listOf<String>()
        if (cell == "a1" || cell == "h8") if (Grid().getVerticalByName("ALPHA", 1).contains(
                        "target")) verticle = Grid().getVerticalByName("ALPHA", 1)

        if (Grid().getVerticalByName(verticals.first().first, verticals.first().second).contains(target))
            verticle = Grid().getVerticalByName(verticals.first().first, verticals.first().second)

        if (Grid().getVerticalByName(verticals.last().first, verticals.last().second).contains(target))
            verticle = Grid().getVerticalByName(verticals.last().first, verticals.last().second)

        val rowSubtract = target.takeLast(1).toInt() - cell.takeLast(1).toInt()
        if (rowSubtract < 0 ) verticle = verticle.reversed()

        val cellIndex = verticle.indexOf(cell)
        val enemy = verticle[cellIndex + 1]

        gridCells.cells[target]!!.second.setState(gridCells.cells[cell]!!.second.getState())
        gridCells.cells[gridCells.cells[cell]!!.second.cell]!!.second.setState(Pair("invisible", 0))
        gridCells.cells[gridCells.cells[enemy]!!.second.cell]!!.second.setState(Pair("invisible", 0))
        isQueen(cell)
        env.updateMoves(init)
        init.changeTurn(env)
    }

}