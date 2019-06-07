package ru.spbstu.checkersapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.counter_score_names.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.game_grid.*
import kotlinx.android.synthetic.main.counter_time_moves.*
import android.widget.*
import ru.spbstu.checkersapp.data.Cell
import ru.spbstu.checkersapp.data.Figure
import ru.spbstu.checkersapp.data.Grid
import ru.spbstu.checkersapp.data.GridCells
import ru.spbstu.checkersapp.logic.Init
import ru.spbstu.checkersapp.logic.TouchHandler
import java.lang.IllegalArgumentException


class GameActivity : AppCompatActivity() {

    // Environment variables
    private val gridCells = GridCells(mutableMapOf())
    private val init = Init(1,
            "First",  "Second",    // Player names
            0, 0,                // Scores
            1)

    fun cellById(id: String): FrameLayout {
        if (id !in Grid().gameCells) throw IllegalArgumentException()
        val cellId= resources.getIdentifier("grid_cell_$id", "id", packageName)
        return findViewById(cellId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

//      init.setNames()

        tb_label.text = init.firstPlayer
        tb_label_hidden.text = init.secondPlayer

        /** Setting actual toolbar and counter labels */
        tb_action.text = getString(R.string.playing_now).toString()
        toolbar_labels_versus.visibility = View.VISIBLE

        fun changePlayer() {
            val second = tb_label_hidden.text
            tb_label_hidden.text = tb_label.text
            tb_label.text = second
        }


        toolbar_default.layoutParams.width = init.normalWidth(resources)
        game_counter_first.layoutParams.width = init.normalWidth(resources)
        game_counter_second.layoutParams.width = init.normalWidth(resources)
        game_grid.layoutParams.width = init.normalWidth(resources)
        game_grid.layoutParams.height = init.normalWidth(resources)

        for (i in 0 until Grid().gameCells.size) when (i) {
            in 0..11 -> gridCells.cells[Grid().gameCells[i]] = Pair(Cell(cellById(Grid().gameCells[i]), "default", "00"), Figure(2,
                    false, Grid().gameCells[i], ImageView(this)).setID().setState(Pair("default", 2)))

            in 12..19 -> gridCells.cells[Grid().gameCells[i]] = Pair(Cell(cellById(Grid().gameCells[i]), "default", "00"), Figure(0,
                    false, Grid().gameCells[i], ImageView(this)).setID().setState(Pair("invisible", 0)))

            in 20..31 -> gridCells.cells[Grid().gameCells[i]] = Pair(Cell(cellById(Grid().gameCells[i]), "default", "00"), Figure(1,
                    false, Grid().gameCells[i], ImageView(this)).setID().setState(Pair("default", 1)))
        }
        gridCells.initTable()

            grid_cells.setOnTouchListener { view, motion ->

                TouchHandler().touchActivityHover(TouchHandler().handleTouch(
                            motion, init.cellWidth(resources)), gridCells, init)
                init.changeTurn()

                true
            }
        }

}
