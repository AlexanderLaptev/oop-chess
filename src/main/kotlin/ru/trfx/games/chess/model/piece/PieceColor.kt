package ru.trfx.games.chess.model.piece

/**
 * Represents a color used in chess. May refer either to the color of a piece, or to a player.
 */
enum class PieceColor {
    /**
     * The color of a black piece.
     */
    Black,

    /**
     * The color of a white piece.
     */
    White;

    val opposite: PieceColor
        get() = when (this) {
            Black -> White
            White -> Black
        }
}
