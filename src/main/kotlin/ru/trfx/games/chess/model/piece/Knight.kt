package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.PlayerMove
import ru.trfx.games.chess.util.PieceHelper

class Knight(color: PieceColor) : Piece(color, 'n') {
    override fun getPossibleMoves(board: BoardModel, rank: Int, file: Int): Collection<PlayerMove> {
        val result = ArrayList<PlayerMove>()
        with(PieceHelper) {
            addMoveIfPossible(color, board, rank + 2, file + 1, result)
            addMoveIfPossible(color, board, rank + 2, file - 1, result)
            addMoveIfPossible(color, board, rank - 2, file + 1, result)
            addMoveIfPossible(color, board, rank - 2, file - 1, result)
            addMoveIfPossible(color, board, rank + 1, file + 2, result)
            addMoveIfPossible(color, board, rank + 1, file - 2, result)
            addMoveIfPossible(color, board, rank - 1, file + 2, result)
            addMoveIfPossible(color, board, rank - 1, file - 2, result)
        }
        return result
    }
}
