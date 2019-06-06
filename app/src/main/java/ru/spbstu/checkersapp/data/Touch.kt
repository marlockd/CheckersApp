package ru.spbstu.checkersapp.data

import android.content.res.Resources
import android.widget.FrameLayout
import ru.spbstu.checkersapp.GameActivity

class Touch {

    fun init(target: String, res: Resources, packageName: String): Int {
        if (target !in Grid().gameCells) return 0
        val cell = GameActivity().cellById(target)
        when (cell.childCount)
        {
            0 -> TODO()
            1 -> TODO()
            else -> TODO()
        }


    }

}