package ru.spbstu.checkersapp.logic

import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.Toolbar
import android.util.DisplayMetrics
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

data class Env(val toolbar: Toolbar, val tbPlayer1: TextView, val tbPlayer2: TextView, val tbVersus: ConstraintLayout,
               val tbAction1: TextView, val tbAction2: TextView, val counterTop: LinearLayout,
               val counterTime: TextView, val counterMoves: TextView, val movesCount: TextView, val counterScore: LinearLayout,
               val scoreFirst: TextView, val scoreSecond: TextView, val gameGrid: ConstraintLayout, val res: DisplayMetrics) {

    val cellWidth = ((374 * res.density) / 8).toInt()
    private fun normalWidth(percent: Double): Int = res.widthPixels - (res.widthPixels * percent).toInt()

    private fun setWidth(width: Int) {
        toolbar.layoutParams.width = width
        counterTop.layoutParams.width = width
        counterScore.layoutParams.width = width
        gameGrid.layoutParams.width = width
        gameGrid.layoutParams.height = width
    }

    fun updateScores(init: Init) {
        scoreFirst.text = init.scoreFirst.toString()
        scoreSecond.text = init.scoreSecond.toString()
    }

    fun updateMoves(init: Init) {
        counterMoves.text = init.movesList.last().second
        counterTime.text = init.movesList.size.toString()
    }

    fun initAll(init: Init, intent: Intent, actual: String) {
        setWidth(normalWidth(0.04))
        tbAction1.text = actual
        tbVersus.visibility = View.VISIBLE
        init.setNames(intent)
        tbPlayer1.text = init.firstPlayer
        tbPlayer2.text = init.secondPlayer
        updateScores(init)
    }

    fun tbChangePlayer() {
        val second = tbPlayer2.text
        tbPlayer2.text = tbPlayer1.text
        tbPlayer1.text = second
    }

}