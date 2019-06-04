package ru.spbstu.checkersapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.counter_score_names.*

class GameActivity : AppCompatActivity() {

    private val player = mutableMapOf<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        for (i in 1 until 2) { player[i] = intent.getStringExtra("player$i") }

        /** Setting actual toolbar and counter labels */
        tb_action.text = getString(R.string.playing_now).toString()
        tb_label.text = player[1]
        counter_player1_name.text = player[1]
        counter_player2_name.text = player[2]

    }
}
