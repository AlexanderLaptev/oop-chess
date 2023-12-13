package ru.trfx.games.chess.view

import ru.trfx.games.chess.Configuration
import java.awt.Color
import java.awt.Component
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JLabel
import javax.swing.SwingConstants

/**
 * A special label which is able to draw a mark in its center.
 */
class MarkedLabel : JLabel() {
    companion object {
        /**
         * The percent of the size of the mark relative to the size of the cell.
         */
        private val MARK_SIZE_PERCENT = Configuration["mark.sizePercent"].toFloat()
    }

    init {
        isOpaque = true // The background is not drawn if the label is not opaque.

        alignmentX = Component.CENTER_ALIGNMENT
        alignmentY = Component.CENTER_ALIGNMENT
        horizontalAlignment = SwingConstants.CENTER
        verticalAlignment = SwingConstants.CENTER
    }

    /**
     * The color of the mark, or null if the dot is hidden.
     */
    var markColor: Color? = null

    /**
     * The color of the tint for this cell, or null if the tint is disabled.
     */
    var tintColor: Color? = null

    override fun paintComponent(g: Graphics?) {
        if (g !is Graphics2D) return

        g.paint = background
        g.fillRect(0, 0, width, height)

        if (markColor != null) {
            val offset = (width * (1.0f - MARK_SIZE_PERCENT) / 2.0f).toInt()
            val size = width - 2 * offset

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g.paint = markColor
            g.fillOval(offset, offset, size, size)
        }

        if (tintColor != null) {
            g.paint = tintColor
            g.fillRect(0, 0, width, height)
        }

        if (icon != null) {
            icon.paintIcon(this, g, 0, 0)
        }
    }
}
