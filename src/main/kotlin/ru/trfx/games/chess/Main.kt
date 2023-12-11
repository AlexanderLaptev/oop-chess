package ru.trfx.games.chess

import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        MainWindow().apply {
            isVisible = true
        }
    }
}
