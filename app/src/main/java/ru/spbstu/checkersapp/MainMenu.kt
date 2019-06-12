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


    }
}
