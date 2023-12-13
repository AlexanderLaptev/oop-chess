package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardSquare
import ru.trfx.games.chess.model.PlayerMove
import ru.trfx.games.chess.util.PieceHelper

class Knight(color: PieceColor) : Piece(color, 'n') {
    override fun getPossibleMoves(board: BoardModel, position: BoardSquare): Collection<PlayerMove> {
        val result = ArrayList<PlayerMove>()
        with(PieceHelper) {
            addMoveIfPossible(color, board, position.rank + 2, position.file + 1, result)
            addMoveIfPossible(color, board, position.rank + 2, position.file - 1, result)
            addMoveIfPossible(color, board, position.rank - 2, position.file + 1, result)
            addMoveIfPossible(color, board, position.rank - 2, position.file - 1, result)
            addMoveIfPossible(color, board, position.rank + 1, position.file + 2, result)
            addMoveIfPossible(color, board, position.rank + 1, position.file - 2, result)
            addMoveIfPossible(color, board, position.rank - 1, position.file + 2, result)
            addMoveIfPossible(color, board, position.rank - 1, position.file - 2, result)
        }
        return result
    }
}
