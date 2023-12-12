package ru.trfx.games.chess.model

import ru.trfx.games.chess.model.piece.Piece

/**
 * Represents a move taken by a player.
 *
 * @param piece The piece that was moved.
 * @param from The initial position of the piece.
 * @param to The final position of the piece.
 */
class PlayerMove(
    val piece: Piece,
    val from: BoardSquare,
    val to: BoardSquare
)
