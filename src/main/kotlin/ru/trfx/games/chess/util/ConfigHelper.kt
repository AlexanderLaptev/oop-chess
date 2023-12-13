package ru.trfx.games.chess.util

import ru.trfx.games.chess.Configuration
import java.awt.Color
import java.awt.Insets
import java.util.regex.Pattern

/**
 * Utility methods for dealing with configs.
 */
object ConfigHelper {
    /**
     * Parses insets from the given configuration option.
     *
     * @param option The name of the configuration option.
     * @return The parsed insets.
     */
    fun parseInsets(option: String): Insets {
        val parts = Configuration[option].split(Pattern.compile(",\\s*"))
        check(parts.size == 4) { "Invalid number of insets: ${parts.size}." }
        return Insets(parts[0].toInt(), parts[1].toInt(), parts[2].toInt(), parts[3].toInt())
    }

    /**
     * Parses a color from the given configuration option. The color must either
     * start with `#`, in which case it is decoded from hex, or be in the form of
     * a comma separated RGB components with optional alpha.
     *
     * @param option The name of the configuration option.
     * @return The parsed color.
     */
    fun parseColor(option: String): Color {
        val value = Configuration[option]
        return if (value.startsWith("#")) {
            Color.decode(value)
        } else {
            val parts = Configuration[option].split(Pattern.compile(",\\s*"))
            check(parts.size in 3..4) { "Invalid number of color components: ${parts.size}." }
            val red = parts[0].toInt()
            val green = parts[1].toInt()
            val blue = parts[2].toInt()
            val alpha = if (parts.size == 4) parts[3].toInt() else 255
            Color(red, green, blue, alpha)
        }
    }
}
