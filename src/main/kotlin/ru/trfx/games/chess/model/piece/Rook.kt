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

    private val _possibleMoves = ArrayList<PlayerMove>()

    override val possibleMoves: Collection<PlayerMove> get() = _possibleMoves

    override fun updatePossibleMoves(board: BoardModel, rank: Int, file: Int) {
        _possibleMoves.clear()
        with(PieceHelper) {
            scanDirection(color, board, rank, file, 0, 1, _possibleMoves)
            scanDirection(color, board, rank, file, 0, -1, _possibleMoves)
            scanDirection(color, board, rank, file, 1, 0, _possibleMoves)
            scanDirection(color, board, rank, file, -1, 0, _possibleMoves)
        }
    }

    override fun onMoved(board: BoardModel, move: PlayerMove) {
        canCastle = false
    }
}
