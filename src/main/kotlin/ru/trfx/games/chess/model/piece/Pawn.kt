package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardSquare
import ru.trfx.games.chess.model.GameState
import ru.trfx.games.chess.model.PlayerMove

class Pawn(private val gameState: GameState, color: PieceColor) : Piece(color, 'p') {
    private val initialRank: Int
        get() = when (color) {
            PieceColor.Black -> 1
            PieceColor.White -> 6
        }

    private class DoubleMove(val pawn: Pawn, to: BoardSquare) : PlayerMove(to)

    class EnPassantMove(val enemyPawn: Pawn, to: BoardSquare) : PlayerMove(to)

    override fun getPossibleMoves(board: BoardModel, position: BoardSquare): Collection<PlayerMove> {
        val deltaRank = when (color) {
            PieceColor.Black -> 1
            PieceColor.White -> -1
        }
        val result = ArrayList<PlayerMove>()

        var rank = position.rank + deltaRank
        val lastMove = gameState.getLastMoveForPlayer(color)
        if (position.file > 0) {
            processDiagonalMove(board, position, rank, -1, lastMove, result)
        }
        if (position.file < BoardModel.BOARD_SIZE - 1) {
            processDiagonalMove(board, position, rank, 1, lastMove, result)
        }

        var piece = board.getValueAt(rank, position.file)
        if (piece == null) {
            result += PlayerMove(BoardSquare(rank, position.file))

            if (position.rank == initialRank) {
                rank += deltaRank
                piece = board.getValueAt(rank, position.file)
                if (piece == null) result += DoubleMove(this, BoardSquare(rank, position.file))
            }
        }
        return result
    }

    private fun processDiagonalMove(
        board: BoardModel,
        position: BoardSquare,
        rank: Int,
        deltaFile: Int,
        lastMove: PlayerMove?,
        accumulator: MutableCollection<PlayerMove>
    ) {
        val file = position.file - deltaFile
        val piece = board.getValueAt(rank, file)
        if (piece != null) {
            if (piece.color != color) accumulator += PlayerMove(BoardSquare(rank, file))
        } else if (lastMove is DoubleMove && lastMove.to.file == file) {
            accumulator += EnPassantMove(lastMove.pawn, BoardSquare(rank, file))
        }
    }
}
