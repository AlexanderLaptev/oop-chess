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
    private val _possibleMoves = mutableListOf<PlayerMove>()

    override val possibleMoves: Collection<PlayerMove> get() = _possibleMoves

    override fun updatePossibleMoves(board: BoardModel, rank: Int, file: Int) {
        _possibleMoves.clear()
        with(PieceHelper) {
            scanDirection(color, board, rank, file, 1, 1, _possibleMoves)
            scanDirection(color, board, rank, file, 1, -1, _possibleMoves)
            scanDirection(color, board, rank, file, -1, 1, _possibleMoves)
            scanDirection(color, board, rank, file, -1, -1, _possibleMoves)
        }
    }
}
