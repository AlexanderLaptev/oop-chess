package ru.trfx.games.chess

import ru.trfx.games.chess.controller.CellClickListener
import ru.trfx.games.chess.controller.GameController
import ru.trfx.games.chess.controller.KeyboardListener
import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.util.ConfigHelper
import ru.trfx.games.chess.view.BoardView
import java.awt.BorderLayout
import java.awt.Font
import javax.imageio.ImageIO
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
        val TITLE = Configuration["window.title"]

        /**
         * The border for the chess board.
         */
        private val CHESS_BOARD_BORDER = EmptyBorder(ConfigHelper.parseInsets("window.boardMargin"))

        /**
         * The border for the status label.
         */
        private val STATUS_LABEL_BORDER = EmptyBorder(ConfigHelper.parseInsets("window.statusLabel.margin"))

        /**
         * The font for the status label.
         */
        private val STATUS_LABEL_FONT = Font(
            null,
            Font.PLAIN,
            Configuration["window.statusLabel.fontSize"].toInt()
        )
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

    /**
     * The game controller.
     */
    private lateinit var gameController: GameController

    init {
        setUpWindow()
        addComponents()
        setUpController()
    }

    /**
     * Performs basic window initialization, such as setting its title and size.
     */
    private fun setUpWindow() {
        title = TITLE
        iconImage = ImageIO.read(this::class.java.getResource("/images/icon.png"))
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(
            Configuration["window.defaultWidth"].toInt(),
            Configuration["window.defaultHeight"].toInt()
        )
        if (Configuration["window.isMaximized"].toBoolean()) maximize()
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

    private fun setUpController() {
        gameController = GameController(boardModel, boardView)
        boardView.addMouseListener(CellClickListener(gameController))
        addKeyListener(KeyboardListener(gameController))
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
