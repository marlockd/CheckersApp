package ru.spbstu.checkersapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game_mode.*
import kotlinx.android.synthetic.main.toolbar.*

class GameMode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_mode)

        /** Setting actual Toolbar labels */
        tb_action.text = getString(R.string.tb_action_player)
        tb_label.text = getString(R.string.tb_label_gamemode)

        /** Bottom Navigation menu visual config */
        bottom_nav_bar.setIconSize(29f, 29f)
        bottom_nav_bar.setTextVisibility(true)
        for (i in 0 until bottom_nav_bar.menu.size()) bottom_nav_bar.setIconTintList(i, null)
        bottom_nav_bar.alpha = (0).toFloat()

        /** Listener for return-back label */
        val returnLinkListener = findViewById<View>(R.id.tv_link_back)
        returnLinkListener.setOnClickListener {
            startActivity(Intent(this, MainMenu::class.java))
        }

        /** Listener for play button */
        val playListener = findViewById<View>(R.id.btn_play)
        playListener.setOnClickListener {
            startActivity(Intent(this, PlayerFirst::class.java))
        }

        /** Listener for multi player button */
        val multiplayerListener = findViewById<View>(R.id.btn_multiplayer)
        multiplayerListener.setOnClickListener {
            Toast.makeText(this, "Multi player is not available yet!!", Toast.LENGTH_SHORT).show()
        }


    }

}
