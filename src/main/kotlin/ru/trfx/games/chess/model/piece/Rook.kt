package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.PlayerMove
import ru.trfx.games.chess.util.PieceHelper

/**
 * Represents the rook chess piece.
 *
 * @param color The color of the rook.
 */
class Rook(color: PieceColor) : Piece(color, 'r') {
    /**
     * Whether this rook can castle.
     */
    var canCastle: Boolean = true
        private set

    override fun getPossibleMoves(board: BoardModel, rank: Int, file: Int): Collection<PlayerMove> {
        val result = ArrayList<PlayerMove>()
        with(PieceHelper) {
            scanDirection(color, board, rank, file, 0, 1, result)
            scanDirection(color, board, rank, file, 0, -1, result)
            scanDirection(color, board, rank, file, 1, 0, result)
            scanDirection(color, board, rank, file, -1, 0, result)
        }
        return result
    }

    override fun onMoved(board: BoardModel, move: PlayerMove) {
        canCastle = false
    }
}
