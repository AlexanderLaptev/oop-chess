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
        private const val QUEENSIDE_FILE = 2
        private const val KINGSIDE_FILE = 6
    }

    private class CastlingMove(val rook: Rook, to: BoardSquare) : PlayerMove(to)

    private var canCastle: Boolean = true

    override fun getPossibleMoves(board: BoardModel, position: BoardSquare): Collection<PlayerMove> {
        val result = ArrayList<PlayerMove>()
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
        accumulator: ArrayList<PlayerMove>
    ) {
        val rook = board.getValueAt(rank, rookFile) as? Rook
        if (rook == null || rook.canCastle) return
        for (file in min(rookFile, KING_FILE) + 1..<max(rookFile, KING_FILE)) {
            if (board.hasPieceAt(rank, file)) return
        }
        accumulator += CastlingMove(rook, BoardSquare(rank, if (rookFile == 0) QUEENSIDE_FILE else KINGSIDE_FILE))
    }

    override fun onMoved(board: BoardModel, move: PlayerMove) {
        canCastle = false
        if (move !is CastlingMove) return
        with(move) {
            if (to.file == QUEENSIDE_FILE) {
                board.setValueAt(null, move.to.rank, 0)
                board.setValueAt(rook, move.to.rank, QUEENSIDE_FILE + 1)
            } else if (to.file == KINGSIDE_FILE) {
                board.setValueAt(null, move.to.rank, BoardModel.BOARD_SIZE - 1)
                board.setValueAt(rook, move.to.rank, KINGSIDE_FILE - 1)
            }
        }
    }
}
