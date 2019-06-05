package ru.spbstu.checkersapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.counter_score_names.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.game_grid.*
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.counter_time_moves.*
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.*


class GameActivity : AppCompatActivity() {

    private val player = mutableMapOf<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

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

        val blues = listOf(
                "b8", "d8", "f8", "h8", "a7", "c7", "e7", "g7", "b6", "d6", "f6", "h6" )
        val oranges = listOf(
                "a1", "c1", "e1", "g1", "b2", "d2", "f2", "h2", "a3", "c3", "e3", "g3" )

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

        for (i in 0 until blues.size) {
            val id= resources.getIdentifier("grid_cell_${blues[i]}", "id", packageName)
            findViewById<FrameLayout>(id).addView(figureList[i])
        }

        for (i in 0 until oranges.size) {
            val id= resources.getIdentifier("grid_cell_${oranges[i]}", "id", packageName)
            findViewById<FrameLayout>(id).addView(figureListOrange[i])
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
