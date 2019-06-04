package ru.spbstu.checkersapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.player_names_form.*
import kotlinx.android.synthetic.main.toolbar.*

class PlayerSecond : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_second)

        tb_action.text = getString(R.string.tb_action_player)
        tb_label.text = getString(R.string.tb_label_player2)

        val name1 = intent.getStringExtra("player1")

        val imgView = findViewById<ImageView>(R.id.player_king_asset)
        imgView.setImageResource(R.drawable.rc_player2_king)
        tv_player_welcome.text = getString(R.string.welcome_text_player2)


        val edtListener = findViewById<View>(R.id.rc_btn_play)
        edtListener.setOnClickListener {

            val playerNameSecond = findViewById<EditText>(R.id.player2_edit_text)

            if (playerNameSecond.text.toString().matches(Regex("""([a-zA-Z]|[а-яА-Я]){2,12}"""))) {

                val intent = Intent(this@PlayerSecond, GameActivity::class.java)
                intent.putExtra("player1", name1)
                intent.putExtra("player2", playerNameSecond.text.toString())
                startActivity(intent)
            } else Toast.makeText(this, "Format error, please try again", Toast.LENGTH_SHORT).show()

        }

    }
}

