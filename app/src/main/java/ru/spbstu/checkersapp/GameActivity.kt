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
import ru.spbstu.checkersapp.data.Grid
import ru.spbstu.checkersapp.logic.TouchHandler
import java.lang.IllegalArgumentException


class GameActivity : AppCompatActivity() {

    private val player = mutableMapOf<Int, String>()

    fun cellById(id: String): FrameLayout {
        if (id !in Grid().gameCells) throw IllegalArgumentException()
        val cellId= resources.getIdentifier("grid_cell_$id", "id", packageName)
        return findViewById(cellId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var moveTurn = 1

//        for (i in 1 until 3) { player[i] = intent.getStringExtra("player$i") }

        /** Setting actual toolbar and counter labels */
        tb_action.text = getString(R.string.playing_now).toString()
        toolbar_labels_versus.visibility = View.VISIBLE
        tb_label.text = "zalupa"
        tb_label_hidden.text = "blyad"

        val displayMetrics = resources.displayMetrics

        val normalWidth = displayMetrics.widthPixels - (displayMetrics.widthPixels * 0.04)

        toolbar_default.layoutParams.width = normalWidth.toInt()
        game_counter_first.layoutParams.width = normalWidth.toInt()
        game_counter_second.layoutParams.width = normalWidth.toInt()
        game_grid.layoutParams.width = normalWidth.toInt()
        game_grid.layoutParams.height = normalWidth.toInt()

        val grid = Grid()

        val figureList = mutableListOf<ImageView>()
        for (i in 0 until 12) {
            figureList.add(ImageView(this))
            figureList[i].id = View.generateViewId()
            figureList[i].setImageResource(R.drawable.rc_figure_blue)
        }

        val figureListOrange = mutableListOf<ImageView>()
        for (i in 0 until 12) {
            figureListOrange.add(ImageView(this))
            figureListOrange[i].id = View.generateViewId()
            figureListOrange[i].setImageResource(R.drawable.rc_figure_orange)
        }

        for (i in 0 until 12) {
            cellById(Grid().orangeStart[i]).addView(figureListOrange[i])
            cellById(Grid().blueStart[i]).addView(figureList[i])
        }

        val octa = ((374 * displayMetrics.density) / 8).toInt()

        val handler = TouchHandler()

        //layout.getChildCount();

        grid_cells.setOnTouchListener {v: View,
                                      m: MotionEvent ->
            val hoverCell = handler.handleTouch(m, octa)
            when(hoverCell)
            {
                in grid.blueStart -> tb_label_hidden.text = "blue"
                in grid.orangeStart -> tb_label_hidden.text = "orange"
                else -> tb_label_hidden.text = "empty cell"
            }




            true
        }

        /** grid_cell_d4.background = getDrawable(R.color.rc_orange)
            grid_cell_g6.setBackgroundColor(getColor(R.color.rc_blue)) */

        /** DEBUG PART */

        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        println("\n/ DEBUG OUTPUT ----------/" +
                "\n\t\tDISPLAY HEIGHT: ${dpHeight.toInt()}dp  &  ${displayMetrics.heightPixels}px" +
                "\n\t\tDISPLAY WIDTH: ${dpWidth.toInt()}dp  ${displayMetrics.widthPixels}px&  " +
                "\n/----------------------------------/")
    }
}
