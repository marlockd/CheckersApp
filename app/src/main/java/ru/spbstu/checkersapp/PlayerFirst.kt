package ru.spbstu.checkersapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.toolbar.*

class PlayerFirst : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_first)

        /** Setting actual text labels for Toolbar */
        tb_action.text = getString(R.string.tb_action_player)
        tb_label.text = getString(R.string.tb_label_player1)

        /** Getting text from EditView and checking before submitting */
        val edtListener = findViewById<View>(R.id.rc_btn_play)
        edtListener.setOnClickListener {

            val playerNameFirst = findViewById<EditText>(R.id.player1_edit_text)

            /** Simple string format check by regular expression
             *  Allowed only a-Z or cyrillic а-Я non wrap strings in 2-12 length */
            if (playerNameFirst.text.toString().matches(Regex("""([a-zA-Z]|[а-яА-Я]){2,12}"""))) {

                val intent = Intent(this@PlayerFirst, PlayerSecond::class.java)
                intent.putExtra("player1", playerNameFirst.text.toString())
                startActivity(intent)

            } else Toast.makeText(this, "Format error, please try again", Toast.LENGTH_SHORT).show()
        }


    }
}
