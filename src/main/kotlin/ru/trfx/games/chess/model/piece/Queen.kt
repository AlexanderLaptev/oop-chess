package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.PlayerMove
import ru.trfx.games.chess.util.PieceHelper

/**
 * Represents the queen chess piece.
 *
 * @param color The color of the queen.
 */
class Queen(color: PieceColor) : Piece(color, 'q') {
    override fun getPossibleMoves(board: BoardModel, rank: Int, file: Int): Collection<PlayerMove> {
        val result = ArrayList<PlayerMove>()
        with(PieceHelper) {
            scanDirection(color, board, rank, file, 0, 1, result)
            scanDirection(color, board, rank, file, 0, -1, result)
            scanDirection(color, board, rank, file, 1, 0, result)
            scanDirection(color, board, rank, file, -1, 0, result)
            scanDirection(color, board, rank, file, 1, 1, result)
            scanDirection(color, board, rank, file, 1, -1, result)
            scanDirection(color, board, rank, file, -1, 1, result)
            scanDirection(color, board, rank, file, -1, -1, result)
        }
        return result
    }
}
