package ru.trfx.games.chess.model.piece

/**
 * Represents a chess piece.
 *
 * @param color The color of the piece.
 * @param blackFileName The file name of the black variant of this piece.
 * @param whiteFileName The file name of the white variant of this piece.
 */
abstract class Piece(
    val color: PieceColor,
    val blackFileName: String,
    val whiteFileName: String,
)
