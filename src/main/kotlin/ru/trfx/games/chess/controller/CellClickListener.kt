package ru.trfx.games.chess.controller

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

/**
 * A `CellClickListener` responds to mouse clicks of the user.
 * Mouse presses are delegated to the [controller].
 *
 * @param controller The game controller.
 */
class CellClickListener(val controller: GameController) : MouseAdapter() {
    override fun mousePressed(e: MouseEvent?) {
        if (e == null) return
        val row = controller.view.rowAtPoint(e.point)
        val column = controller.view.columnAtPoint(e.point)
        controller.onSquareClicked(row, column)
    }
}
