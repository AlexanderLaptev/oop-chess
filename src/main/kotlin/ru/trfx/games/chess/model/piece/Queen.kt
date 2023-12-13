package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardSquare
import ru.trfx.games.chess.model.PlayerMove
import ru.trfx.games.chess.util.PieceHelper

/**
 * Represents the queen chess piece.
 *
 * @param color The color of the queen.
 */
class Queen(color: PieceColor) : Piece(color, 'q') {
    override fun getPossibleMoves(board: BoardModel, position: BoardSquare): Collection<PlayerMove> {
        val result = ArrayList<PlayerMove>()
        with(PieceHelper) {
            scanDirection(color, board, position, 0, 1, result)
            scanDirection(color, board, position, 0, -1, result)
            scanDirection(color, board, position, 1, 0, result)
            scanDirection(color, board, position, -1, 0, result)
            scanDirection(color, board, position, 1, 1, result)
            scanDirection(color, board, position, 1, -1, result)
            scanDirection(color, board, position, -1, 1, result)
            scanDirection(color, board, position, -1, -1, result)
        }
        return result
    }
}
