package ru.trfx.games.chess

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.view.BoardView
import java.awt.BorderLayout
import java.awt.Font
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

/**
 * The main window of the application.
 */
class MainWindow : JFrame() {
    companion object {
        /**
         * The title of the main window.
         */
        const val TITLE = "KChess v0.1.0"
    }

    init {
        setUpWindow()
        addComponents()
    }

    /**
     * Performs basic window initialization, such as setting its title and size.
     */
    private fun setUpWindow() {
        title = TITLE
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(
            Configuration["window.defaultWidth"]!!.toInt(),
            Configuration["window.defaultHeight"]!!.toInt()
        )
        if (Configuration["window.isMaximized"]!!.toBoolean()) maximize()
        centerOnScreen()
    }

    private fun addComponents() {
        val panel = JPanel().apply {
            border = EmptyBorder(20, 30, 20, 30)
        }
        val statusLabel = JLabel("Test status.").apply {
            font = Font(null, Font.PLAIN, 14)
            border = EmptyBorder(0, 12, 4, 0)
        }

        panel.add(BoardView(BoardModel()))
        add(panel, BorderLayout.CENTER)
        add(statusLabel, BorderLayout.PAGE_END)
    }

    /**
     * Maximizes the window in both directions.
     */
    private fun maximize() {
        extendedState = extendedState or MAXIMIZED_BOTH
    }

    /**
     * Centers the window on the screen.
     */
    private fun centerOnScreen() = setLocationRelativeTo(null)
}
