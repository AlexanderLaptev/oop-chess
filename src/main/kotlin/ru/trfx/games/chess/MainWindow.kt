package ru.trfx.games.chess

import javax.swing.JFrame

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
