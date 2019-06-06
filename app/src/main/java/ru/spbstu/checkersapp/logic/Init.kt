package ru.spbstu.checkersapp.logic

import android.provider.Settings.Global.getString
import ru.spbstu.checkersapp.R
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
import ru.spbstu.checkersapp.logic.TouchHandler
import java.lang.IllegalArgumentException

class Init {

    fun touchInit(cell: String) {

    }

    /** Setting actual toolbar and counter labels
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
    game_grid.layoutParams.height = normalWidth.toInt()     */

}