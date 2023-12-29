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
    private val _possibleMoves = mutableListOf<PlayerMove>()

    override val possibleMoves: Collection<PlayerMove> get() = _possibleMoves

    override fun updatePossibleMoves(board: BoardModel, rank: Int, file: Int) {
        _possibleMoves.clear()
        with(PieceHelper) {
            scanDirection(color, board, rank, file, 0, 1, _possibleMoves)
            scanDirection(color, board, rank, file, 0, -1, _possibleMoves)
            scanDirection(color, board, rank, file, 1, 0, _possibleMoves)
            scanDirection(color, board, rank, file, -1, 0, _possibleMoves)
            scanDirection(color, board, rank, file, 1, 1, _possibleMoves)
            scanDirection(color, board, rank, file, 1, -1, _possibleMoves)
            scanDirection(color, board, rank, file, -1, 1, _possibleMoves)
            scanDirection(color, board, rank, file, -1, -1, _possibleMoves)
        }
    }
}
