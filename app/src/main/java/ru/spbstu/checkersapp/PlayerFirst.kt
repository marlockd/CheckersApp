package ru.spbstu.checkersapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_player_first.*
import kotlinx.android.synthetic.main.player_names_form.*
import kotlinx.android.synthetic.main.toolbar.*

class PlayerFirst : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_first)

        tb_action.text = getString(R.string.tb_action_player)
        tb_label.text = getString(R.string.tb_label_player1)

        val imgView = findViewById<ImageView>(R.id.player_king_asset)
        imgView.setImageResource(R.drawable.rc_player1_king)
        tv_player_welcome.text = getString(R.string.welcome_text_player1)

        val edtListener = findViewById<View>(R.id.rc_btn_play)
        edtListener.setOnClickListener {
            val playerNameFirst = findViewById<EditText>(R.id.player1_edit_text)

            if (playerNameFirst.text.toString().matches(Regex("""([a-zA-Z]|[а-яА-Я]){2,12}"""))) {
                val intent = Intent(this@PlayerFirst, PlayerSecond::class.java)
                intent.putExtra("player1", playerNameFirst.text.toString())
                startActivity(intent)
            } else
                Toast.makeText(this, "Format error, please try again", Toast.LENGTH_SHORT).show()

        }


    }
}
