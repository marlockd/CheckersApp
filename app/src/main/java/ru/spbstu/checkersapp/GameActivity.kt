package ru.spbstu.checkersapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.counter_score_names.*
import kotlinx.android.synthetic.main.counter_time_moves.*
import kotlinx.android.synthetic.main.game_grid.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.spbstu.checkersapp.data.Cell
import ru.spbstu.checkersapp.data.Figure
import ru.spbstu.checkersapp.data.Grid
import ru.spbstu.checkersapp.data.GridCells
import ru.spbstu.checkersapp.logic.Env
import ru.spbstu.checkersapp.logic.Init
import ru.spbstu.checkersapp.logic.TouchHandler


class GameActivity : AppCompatActivity() {

    // Environment variables
    private val gridCells = GridCells(mutableMapOf())
    private val init = Init(1, "First", "Second",
            false, 0, 0, mutableListOf())

    fun cellById(id: String): FrameLayout = findViewById(resources.getIdentifier(
            "grid_cell_$id", "id", packageName)) ?: throw IllegalArgumentException()

    fun endGame(team: Int) {
        var winner = ""
        if (team == 1) winner = init.firstPlayer
        if (team == 2) winner = init.secondPlayer
        Toast.makeText(this, "Game over! Winner is: $winner, overall moves count: ${init.movesList.size}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val env = Env(toolbar_default, tb_label, tb_label_hidden, toolbar_labels_versus, tb_action,
                tb_action_hidden, game_counter_first, counter_top_time, counter_top_moves, counter_top_moves_count,
                game_counter_second, counter_score_player1, counter_score_player2, game_grid, resources.displayMetrics)

        env.initAll(init, intent, getString(R.string.playing_now))

        for (i in 0 until Grid().gameCells.size) when (i) {
            in 0..11 -> gridCells.cells[Grid().gameCells[i]] = Pair(Cell(cellById(Grid().gameCells[i]), "default", "00"),
                    Figure(2, false, Grid().gameCells[i], ImageView(this)).setID().setState(Pair("default", 2)))
            in 12..19 -> gridCells.cells[Grid().gameCells[i]] = Pair(Cell(cellById(Grid().gameCells[i]), "default", "00"),
                    Figure(0, false, Grid().gameCells[i], ImageView(this)).setID().setState(Pair("invisible", 0)))
            in 20..31 -> gridCells.cells[Grid().gameCells[i]] = Pair(Cell(cellById(Grid().gameCells[i]), "default", "00"),
                    Figure(1, false, Grid().gameCells[i], ImageView(this)).setID().setState(Pair("default", 1)))
        }
        gridCells.initTable()

        //debug
        //gridCells.cells["c3"]!!.second.setState(Pair("queen", 1))

        grid_cells.setOnTouchListener { view, motion ->
            TouchHandler(gridCells, init, env).touchActivityHover(TouchHandler(gridCells, init, env).handleTouch(motion, env.cellWidth))
            true
        }
    }


}
