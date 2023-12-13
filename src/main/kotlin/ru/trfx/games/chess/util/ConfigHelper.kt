package ru.trfx.games.chess.util

import ru.trfx.games.chess.Configuration
import java.awt.Insets
import java.util.regex.Pattern

/**
 * Utility methods for dealing with configs.
 */
object ConfigHelper {
    fun parseInsets(option: String): Insets {
        val parts = Configuration[option]!!.split(Pattern.compile(",\\s*"))
        check(parts.size == 4) { "Invalid number of insets: ${parts.size}." }
        return Insets(parts[0].toInt(), parts[1].toInt(), parts[2].toInt(), parts[3].toInt())
    }
}
