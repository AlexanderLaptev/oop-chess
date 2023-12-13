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

        /**
         * The border for the chess board.
         */
        private val CHESS_BOARD_BORDER = EmptyBorder(20, 30, 20, 30)

        /**
         * The border for the status label.
         */
        private val STATUS_LABEL_BORDER = EmptyBorder(0, 8, 4, 0)

        /**
         * The font for the status label.
         */
        private val STATUS_LABEL_FONT = Font(null, Font.PLAIN, 14)
    }

    /**
     * The chess board view.
     */
    private lateinit var boardView: BoardView

    /**
     * The status label. This label is located at the bottom of the window.
     */
    private lateinit var statusLabel: JLabel

    /**
     * The board model.
     */
    private val boardModel = BoardModel()

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

    /**
     * Populates the window with the necessary components.
     */
    private fun addComponents() {
        val panel = JPanel().apply {
            border = CHESS_BOARD_BORDER
        }

        statusLabel = JLabel("N/A").apply {
            font = STATUS_LABEL_FONT
            border = STATUS_LABEL_BORDER
        }

        boardView = BoardView(boardModel)
        panel.add(boardView)
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
