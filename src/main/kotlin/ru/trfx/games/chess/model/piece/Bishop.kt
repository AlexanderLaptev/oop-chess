package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.PlayerMove
import ru.trfx.games.chess.util.PieceHelper

/**
 * Represents the bishop chess piece.
 *
 * @param color The color of the bishop.
 */
class Bishop(color: PieceColor) : Piece(color, 'b') {
    override fun getPossibleMoves(board: BoardModel, rank: Int, file: Int): Collection<PlayerMove> {
        val result = ArrayList<PlayerMove>()
        with(PieceHelper) {
            scanDirection(color, board, rank, file, 1, 1, result)
            scanDirection(color, board, rank, file, 1, -1, result)
            scanDirection(color, board, rank, file, -1, 1, result)
            scanDirection(color, board, rank, file, -1, -1, result)
        }
        return result
    }
}
