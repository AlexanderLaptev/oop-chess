package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardSquare
import ru.trfx.games.chess.model.PlayerMove
import ru.trfx.games.chess.util.PieceHelper
import kotlin.math.max
import kotlin.math.min

/**
 * Represents the king chess piece.
 *
 * @param color The color of the king.
 */
class King(color: PieceColor) : Piece(color, 'k') {
    companion object {
        private const val KING_FILE = 4
    }

    private var canCastle: Boolean = true

    override fun getPossibleMoves(board: BoardModel, position: BoardSquare): Collection<BoardSquare> {
        val result = ArrayList<BoardSquare>()
        with(PieceHelper) {
            addMoveIfPossible(color, board, position.rank - 1, position.file - 1, result)
            addMoveIfPossible(color, board, position.rank - 1, position.file, result)
            addMoveIfPossible(color, board, position.rank - 1, position.file + 1, result)
            addMoveIfPossible(color, board, position.rank, position.file + 1, result)
            addMoveIfPossible(color, board, position.rank, position.file - 1, result)
            addMoveIfPossible(color, board, position.rank + 1, position.file - 1, result)
            addMoveIfPossible(color, board, position.rank + 1, position.file, result)
            addMoveIfPossible(color, board, position.rank + 1, position.file + 1, result)
        }

        if (canCastle) {
            addCastlingMoveIfPossible(board, position.rank, 0, result)
            addCastlingMoveIfPossible(board, position.rank, BoardModel.BOARD_SIZE - 1, result)
        }
        return result
    }

    private fun addCastlingMoveIfPossible(
        board: BoardModel,
        rank: Int,
        rookFile: Int,
        accumulator: MutableCollection<BoardSquare>
    ) {
        val rook = board.getValueAt(rank, rookFile) as? Rook
        if (rook == null || rook.canCastle) return
        for (file in min(rookFile, KING_FILE) + 1..<max(rookFile, KING_FILE)) {
            if (board.hasPieceAt(rank, file)) return
        }
        accumulator += BoardSquare(rank, if (rookFile == 0) 2 else 6)
    }

    override fun onMoved(board: BoardModel, move: PlayerMove) {
        canCastle = false
    }
}
