package ru.trfx.games.chess.controller

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

/**
 * A `KeyboardListener` responds to key presses of the player.
 * Currently, the only supported key shortcut is clearing the selection with `Escape`.
 *
 * @param controller The game controller.
 */
class KeyboardListener(val controller: GameController) : KeyAdapter() {
    override fun keyPressed(e: KeyEvent?) {
        if (e != null && e.keyCode == KeyEvent.VK_ESCAPE) controller.clearCellSelection()
    }
}
