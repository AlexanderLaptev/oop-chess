package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardSquare
import ru.trfx.games.chess.model.PlayerMove
import ru.trfx.games.chess.util.BoardScanner

/**
 * Represents the rook chess piece.
 *
 * @param color The color of the rook.
 */
class Rook(color: PieceColor) : Piece(color, 'r') {
    /**
     * Whether this rook can castle.
     */
    var canCastle: Boolean = false
        private set

    override fun getPossibleMoves(board: BoardModel, position: BoardSquare): Collection<BoardSquare> {
        val result = ArrayList<BoardSquare>()
        with(BoardScanner) {
            scanDirection(color, board, position, 0, 1, result)
            scanDirection(color, board, position, 0, -1, result)
            scanDirection(color, board, position, 1, 0, result)
            scanDirection(color, board, position, -1, 0, result)
        }
        return result
    }

    override fun onMoved(board: BoardModel, move: PlayerMove) {
        canCastle = false
    }
}
