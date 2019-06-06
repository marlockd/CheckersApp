package ru.spbstu.checkersapp.data

import ru.spbstu.checkersapp.GameActivity

class Grid {

    /**

    VISUAL LIST BY INDEX KEYS

    [ff] [00] [ff] [01] [ff] [02] [ff] [03]
    [04] [ff] [05] [ff] [06] [ff] [07] [ff]
    [ff] [08] [ff] [09] [ff] [10] [ff] [11]
    [12] [ff] [13] [ff] [14] [ff] [15] [ff]
    [ff] [16] [ff] [17] [ff] [18] [ff] [19]
    [20] [ff] [21] [ff] [22] [ff] [23] [ff]
    [ff] [24] [ff] [25] [ff] [26] [ff] [27]
    [28] [ff] [29] [ff] [30] [ff] [31] [ff]

    INDEX AUGMENTATION TREE
      [01]     - cell
    4/    \5   - index augmentation

    AUGMENTS INLINE

           A    B    C    D    E    F    G    H
      _____________________________________________
    8 |       [00]      [01]      [02]      [03]  | 8
      |      4    5    4    5    4    5    4      |
    7 |  [04]      [05]      [06]      [07]       | 7
      |      4    3    4    3    4    3    4      |
    6 |       [08]      [09]      [10]      [11]  | 6
      |      4    5    4    5    4    5    4      |
    5 |  [12]      [13]      [14]      [15]       | 5
      |      4    3    4    3    4    3    4      |
    4 |       [16]      [17]      [18]      [19]  | 4
      |      4    5    4    5    4    5    4      |
    3 |  [20]      [21]      [22]      [23]       | 3
      |      4    3    4    3    4    3    4      |
    2 |       [24]      [25]      [26]      [27]  | 2
      |      4    5    4    5    4    5    4      |
    1 |  [28]      [29]      [30]      [31]       | 1
      |___________________________________________|
          A    B    C    D    E    F    G    H

    BASE MOVES VERTICALS

    LEFT TO TOP

    1.     a7:b8                     ->  [4]
    1.     g1:h2                     ->  [4]
    2.     a5:b6:c7:d8               ->  [4, 3, 4]
    2.     e1:f2:g3:h4               ->  [4, 3, 4]
    3.     a3:b4:c5:d6:e7:f8         ->  [4, 3, 4, 3, 4]
    3.     c1:d2:e3:f4:g5:h6         ->  [4, 3, 4, 3, 4]
    4.     a1:b2:c3:d4:e5:f6:g7:h8   ->  [4, 3, 4, 3, 4, 3, 4]

    RIGHT TO TOP

    5.     h6:g7:f8                  ->  [4, 5]
    5.     c1:b2:a3                  ->  [5, 4]
    6.     h4:g5:f6:e7:d8            ->  [4, 5, 4, 5]
    6.     e1:d2:c3:b4:a5            ->  [5, 4, 5, 4]
    7.     h2:g3:f4:e5:d6:c7:b8      ->  [4, 5, 4, 5, 4, 5]
    7.     g1:f2:e3:d4:c5:b6:a7      ->  [5, 4, 5, 4, 5, 4]

     OVERALL PREDEFINED MOVES LIST:

    1.     OMEGA       ->  { (a7; b8), (g1; h2) }
    2.     GAMMA       ->  { (a5; b6; c7; d8), (e1; f2; g3; h4) }
    3.     BETA        ->  { (a3; b4; c5; d6; e7; f8), (c1; d2; e3; f4; g5; h6) }
    4.     ALPHA       ->  { (a1; b2; c3; d4; e5; f6; g7; h8) }

    5.     DELTA       ->  { (h6; g7; f8), (c1; b2; a3) }
    6.     SIGMA       ->  { (h4; g5; f6; e7; d8), (e1; d2; c3; b4; a5) }
    7.     EPSILON     ->  { (h2; g3; f4; e5; d6; c7; b8), (g1; f2; e3; d4; c5; b6; a7) }
     */

    val verticals = mapOf(
            "OMEGA" to Pair(listOf("a7", "b8"),
                    listOf("g1", "h2")),
            "GAMMA" to Pair(listOf("a5", "b6", "c7", "d8"),
                    listOf("e1", "f2", "g3", "h4")),
            "BETA" to Pair(listOf("a3", "b4", "c5", "d6", "e7", "f8"),
                    listOf("c1", "d2", "e3", "f4", "g5", "h6")),
            "ALPHA" to Pair(listOf("a1", "b2", "c3", "d4", "e5", "f6", "g7", "h8"),
                    listOf()),
            "DELTA" to Pair(listOf("h6", "g7", "f8"),
                    listOf("c1", "b2", "a3")),
            "SIGMA" to Pair(listOf("h4", "g5", "f6", "e7", "d8"),
                    listOf("e1", "d2", "c3", "b4", "a5")),
            "EPSILON" to Pair(listOf("h2", "g3", "f4", "e5", "d6", "c7", "b8"),
                    listOf("g1", "f2", "e3", "d4", "c5", "b6", "a7"))
    )

    val gameCells = listOf(
                  "b8",       "d8",       "f8",      "h8",
            "a7",       "c7",       "e7",       "g7",
                  "b6",       "d6",       "f6",      "h6",
            "a5",       "c5",       "e5",       "g5",
                  "b4",       "d4",       "f4",      "h4",
            "a3",       "c3",       "e3",       "g3",
                  "b2",       "d2",       "f2",      "h2",
            "a1",       "c1",       "e1",       "g1"
    )

    val orangeStart = listOf(
                  "b8",       "d8",       "f8",      "h8",
            "a7",       "c7",       "e7",      "g7",
                  "b6",       "d6",       "f6",      "h6"
    )

    val blueStart = listOf(
            "a3",       "c3",       "e3",       "g3",
                  "b2",       "d2",       "f2",       "h2",
            "a1",       "c1",       "e1",       "g1"
    )

    fun verticalsCheck(cell: String): List<Pair<String, Int>> {
        val result = mutableListOf<Pair<String, Int>>()
        verticals.forEach {
            if (cell in it.value.first) result.add(Pair(it.key, 1))
            else if (cell in it.value.second) result.add(Pair(it.key, 2))
        }
        return result
    }

    fun getVerticalByName(name: String, num: Int): List<String> {
        if (name !in verticals.keys || num !in 1..2 || (name == "ALPHA" && num == 2))
            throw IllegalArgumentException()

        return when (num) {
            1 -> verticals.getValue(name).first
            2 -> verticals.getValue(name).second
            else -> throw IllegalArgumentException()
        }
    }

    private fun verticalCheck(vertical: List<String>): Boolean {
        verticals.values.forEach { if (vertical == it.first || vertical == it.second) return true }
        return false
    }

    fun isEmptyInRange(start: Int, end: Int, vertical: List<String>): Boolean {
        if (!verticalCheck(vertical)) throw IllegalArgumentException()
        if (start !in 0..vertical.size || end !in 0..vertical.size) return false
        for (i in start until end) if (!GameActivity().gridCells.isEmpty(vertical[i])) return false
        return true
    }

    // (val player: Int, var isQueen: Boolean, var cell: String, var view: ImageView)

}
