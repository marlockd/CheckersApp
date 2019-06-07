package ru.spbstu.checkersapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        /** Listener for play button */
        val playListener = findViewById<View>(R.id.btn_play)
        playListener.setOnClickListener {
            startActivity(Intent(this, GameMode::class.java))
        }


        /** activities info & rules not finished yet

        val rulesListener = findViewById<View>(R.id.btn_rules)
        rulesListener.setOnClickListener {
            startActivity(Intent(this, AppInfo::class.java))
        }


        val infoListener = findViewById<View>(R.id.tv_credits)
        infoListener.setOnClickListener {
            startActivity(Intent(this, AppInfo::class.java))
        }

        */

    }
}
