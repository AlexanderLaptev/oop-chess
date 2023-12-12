package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardSquare

class Knight(color: PieceColor) : Piece(color, 'n') {
    override fun getPossibleMoves(board: BoardModel, position: BoardSquare): Collection<BoardSquare> {
        val result = ArrayList<BoardSquare>()
        addMoveIfPossible(board, position.rank + 2, position.file + 1, result)
        addMoveIfPossible(board, position.rank + 2, position.file - 1, result)
        addMoveIfPossible(board, position.rank - 2, position.file + 1, result)
        addMoveIfPossible(board, position.rank - 2, position.file - 1, result)
        addMoveIfPossible(board, position.rank + 1, position.file + 2, result)
        addMoveIfPossible(board, position.rank + 1, position.file - 2, result)
        addMoveIfPossible(board, position.rank - 1, position.file + 2, result)
        addMoveIfPossible(board, position.rank - 1, position.file - 2, result)
        return result
    }

    private fun addMoveIfPossible(
        board: BoardModel,
        rank: Int,
        file: Int,
        accumulator: MutableCollection<BoardSquare>
    ) {
        if (!BoardSquare.areCoordinatesValid(rank, file)) return
        val piece = board.getValueAt(rank, file)
        if (piece == null || piece.color == color) return
        accumulator += BoardSquare(rank, file)
    }
}
