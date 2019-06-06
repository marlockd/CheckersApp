package ru.spbstu.checkersapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.counter_score_names.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.game_grid.*
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import kotlinx.android.synthetic.main.counter_time_moves.*
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.*
import ru.spbstu.checkersapp.data.Figure
import ru.spbstu.checkersapp.data.Grid
import ru.spbstu.checkersapp.data.GridCells
import ru.spbstu.checkersapp.logic.Move
import ru.spbstu.checkersapp.logic.TouchHandler
import java.lang.IllegalArgumentException


class GameActivity : AppCompatActivity() {

    private val player = mutableMapOf<Int, String>()
    val gridCells = GridCells(mutableMapOf())

    fun cellById(id: String): FrameLayout {
        if (id !in Grid().gameCells) throw IllegalArgumentException()
        val cellId= resources.getIdentifier("grid_cell_$id", "id", packageName)
        return findViewById(cellId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

//        for (i in 1 until 3) { player[i] = intent.getStringExtra("player$i") }

        /** Setting actual toolbar and counter labels */
        tb_action.text = getString(R.string.playing_now).toString()
        toolbar_labels_versus.visibility = View.VISIBLE
        tb_label.text = "zalupa"
        tb_label_hidden.text = "blyad"

        val normalWidth =
                resources.displayMetrics.widthPixels - (resources.displayMetrics.widthPixels * 0.04)

        toolbar_default.layoutParams.width = normalWidth.toInt()
        game_counter_first.layoutParams.width = normalWidth.toInt()
        game_counter_second.layoutParams.width = normalWidth.toInt()
        game_grid.layoutParams.width = normalWidth.toInt()
        game_grid.layoutParams.height = normalWidth.toInt()


        for (i in 0 until Grid().gameCells.size) when (i) {
            in 0..11 -> gridCells.cells.put(Grid().gameCells[i], Pair(cellById(Grid().gameCells[i]), Figure(2,
                    false, Grid().gameCells[i], ImageView(this)).setID().setState("default")))
            in 12..19 -> gridCells.cells.put(Grid().gameCells[i], Pair(cellById(Grid().gameCells[i]), Figure(0,
                    false, Grid().gameCells[i], ImageView(this)).setID().setState("invisible")))
            in 20..31 -> gridCells.cells.put(Grid().gameCells[i], Pair(cellById(Grid().gameCells[i]), Figure(1,
                    false, Grid().gameCells[i], ImageView(this)).setID().setState("default")))
        }

        gridCells.init()

        gridCells.cells["d4"]!!.second.player = 2
        gridCells.cells["d4"]!!.second.setState("queen")

        println(Move(this).targetCheck(gridCells.cells["c3"]!!.second, "d4", gridCells))

        gridCells.cells["d4"]!!.second.setState("invisible")

        println(Move(this).targetCheck(gridCells.cells["c3"]!!.second, "d4", gridCells))


        /**
        gridCells.cells["e3"]!!.first.setBackgroundResource(R.color.rc_grid_square_move)
        gridCells.cells["e3"]!!.second.setState("queen")

        gridCells.cells["d2"]!!.first.setBackgroundResource(R.color.rc_grid_square_attack)
        gridCells.cells["d2"]!!.second.setState("queen")

        gridCells.cells["d4"]!!.first.setBackgroundResource(R.color.rc_grid_square_move)
        gridCells.cells["b8"]!!.second.setState("queen")

        gridCells.cells["g5"]!!.first.setBackgroundResource(R.color.rc_grid_square_attack)
        gridCells.cells["a1"]!!.second.setState("queen") */


        val octa = ((374 * resources.displayMetrics.density) / 8).toInt()

        val handler = TouchHandler()


        grid_cells.setOnTouchListener { view, motion ->
            val hoverCell = handler.handleTouch(motion, octa)

            true
        }


    }
}
