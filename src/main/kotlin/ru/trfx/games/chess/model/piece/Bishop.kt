package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardSquare
import ru.trfx.games.chess.util.PieceHelper

/**
 * Represents the bishop chess piece.
 *
 * @param color The color of the bishop.
 */
class Bishop(color: PieceColor) : Piece(color, 'b') {
    override fun getPossibleMoves(board: BoardModel, position: BoardSquare): Collection<BoardSquare> {
        val result = ArrayList<BoardSquare>()
        with(PieceHelper) {
            scanDirection(color, board, position, 1, 1, result)
            scanDirection(color, board, position, 1, -1, result)
            scanDirection(color, board, position, -1, 1, result)
            scanDirection(color, board, position, -1, -1, result)
        }
        return result
    }
}
