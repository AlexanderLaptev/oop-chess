package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.PlayerMove
import ru.trfx.games.chess.util.PieceHelper

class Knight(color: PieceColor) : Piece(color, 'n') {
    private val _possibleMoves = mutableListOf<PlayerMove>()

    override val possibleMoves: Collection<PlayerMove> get() = _possibleMoves

    override fun updatePossibleMoves(board: BoardModel, rank: Int, file: Int) {
        _possibleMoves.clear()
        with(PieceHelper) {
            addMoveIfPossible(color, board, rank + 2, file + 1, _possibleMoves)
            addMoveIfPossible(color, board, rank + 2, file - 1, _possibleMoves)
            addMoveIfPossible(color, board, rank - 2, file + 1, _possibleMoves)
            addMoveIfPossible(color, board, rank - 2, file - 1, _possibleMoves)
            addMoveIfPossible(color, board, rank + 1, file + 2, _possibleMoves)
            addMoveIfPossible(color, board, rank + 1, file - 2, _possibleMoves)
            addMoveIfPossible(color, board, rank - 1, file + 2, _possibleMoves)
            addMoveIfPossible(color, board, rank - 1, file - 2, _possibleMoves)
        }
    }
}
