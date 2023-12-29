package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.controller.MessageBoxProxy
import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardSquare
import ru.trfx.games.chess.model.GameState
import ru.trfx.games.chess.model.PlayerMove

class Pawn(private val gameState: GameState, color: PieceColor) : Piece(color, 'p') {
    private val initialRank: Int = when (color) {
        PieceColor.Black -> 1
        PieceColor.White -> BoardModel.BOARD_SIZE - 2
    }

    private val promotionRank: Int = when (color) {
        PieceColor.Black -> BoardModel.BOARD_SIZE - 1
        PieceColor.White -> 0
    }

    private val _possibleMoves = mutableListOf<PlayerMove>()

    override val possibleMoves: Collection<PlayerMove> get() = _possibleMoves

    private class DoubleMove(val pawn: Pawn, to: BoardSquare) : PlayerMove(to)

    private class EnPassantMove(val lastMove: DoubleMove, to: BoardSquare) : PlayerMove(to)

    override fun updatePossibleMoves(board: BoardModel, rank: Int, file: Int) {
        _possibleMoves.clear()

        val deltaRank = when (color) {
            PieceColor.Black -> 1
            PieceColor.White -> -1
        }

        var currentRank = rank + deltaRank
        if (!BoardSquare.isCoordinateValid(currentRank)) return

        val lastMove = gameState.getStateForPlayer(color.opposite).lastMove
        if (file > 0) {
            processDiagonalMove(board, rank, file, currentRank, -1, lastMove, _possibleMoves)
        }
        if (file < BoardModel.BOARD_SIZE - 1) {
            processDiagonalMove(board, rank, file, currentRank, 1, lastMove, _possibleMoves)
        }

        var piece = board.getValueAt(currentRank, file)
        if (piece == null) {
            _possibleMoves += PlayerMove(BoardSquare(currentRank, file))

            if (rank == initialRank) {
                currentRank += deltaRank
                piece = board.getValueAt(currentRank, file)
                if (piece == null) _possibleMoves += DoubleMove(this, BoardSquare(currentRank, file))
            }
        }
    }

    private fun processDiagonalMove(
        board: BoardModel,
        rank: Int,
        file: Int,
        moveRank: Int,
        deltaFile: Int,
        lastMove: PlayerMove?,
        accumulator: MutableCollection<PlayerMove>
    ) {
        if (!BoardSquare.isCoordinateValid(moveRank)) return
        val currentFile = file + deltaFile
        val piece = board.getValueAt(moveRank, currentFile)
        if (piece != null) {
            if (piece.color != color) accumulator += PlayerMove(BoardSquare(moveRank, currentFile))
        } else if (lastMove is DoubleMove && lastMove.to.file == currentFile && lastMove.to.rank == rank) {
            accumulator += EnPassantMove(lastMove, BoardSquare(moveRank, currentFile))
        }
    }

    override fun onMoved(board: BoardModel, move: PlayerMove) {
        if (move is EnPassantMove) board.setValueAt(null, move.lastMove.to.rank, move.lastMove.to.file)
        else if (move.to.rank == promotionRank) {
            val result = MessageBoxProxy.showPromotionDialog(color)
            board.setValueAt(result, move.to.rank, move.to.file)
        }
    }
}
