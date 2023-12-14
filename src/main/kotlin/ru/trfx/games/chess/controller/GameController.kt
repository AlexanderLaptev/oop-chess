package ru.trfx.games.chess.controller

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardModel.HighlightType
import ru.trfx.games.chess.model.BoardModel.MarkType
import ru.trfx.games.chess.model.GameState
import ru.trfx.games.chess.model.PlayerMove
import ru.trfx.games.chess.model.piece.Bishop
import ru.trfx.games.chess.model.piece.King
import ru.trfx.games.chess.model.piece.Knight
import ru.trfx.games.chess.model.piece.Pawn
import ru.trfx.games.chess.model.piece.Piece
import ru.trfx.games.chess.model.piece.PieceColor
import ru.trfx.games.chess.model.piece.PieceColor.Black
import ru.trfx.games.chess.model.piece.PieceColor.White
import ru.trfx.games.chess.model.piece.Queen
import ru.trfx.games.chess.model.piece.Rook
import ru.trfx.games.chess.view.BoardView

class GameController(val board: BoardModel, val view: BoardView) {
    companion object {
        private const val UNSELECTED_INDEX = -1
    }

    private data class KingPosition(
        val king: King,
        var rank: Int,
        var file: Int
    )

    private val gameState = GameState()

    private var currentPlayer: PieceColor = White

    private var selectedPiece: Piece? = null

    private var selectedRank: Int = UNSELECTED_INDEX

    private var selectedFile: Int = UNSELECTED_INDEX

    private var blackPieces: MutableCollection<Piece> = ArrayList()

    private var whitePieces: MutableCollection<Piece> = ArrayList()

    private lateinit var blackKingPosition: KingPosition

    private lateinit var whiteKingPosition: KingPosition

    private val enemyPieces: Collection<Piece>
        get() = when (currentPlayer) {
            Black -> blackPieces
            White -> whitePieces
        }

    init {
        placePieces()
        collectPieces()
    }

    private fun collectPieces() {
        for (i in 0..<BoardModel.BOARD_SIZE) {
            for (j in 0..<BoardModel.BOARD_SIZE) {
                val piece = board.getValueAt(i, j) ?: continue
                when (piece.color) {
                    Black -> blackPieces += piece
                    White -> whitePieces += piece
                }
            }
        }
    }

    private fun placePieces() {
        blackKingPosition = KingPosition(King(Black), 0, 4)
        board.setValueAt(blackKingPosition.king, 0, 4)
        board.setValueAt(Rook(Black), 0, 0)
        board.setValueAt(Knight(Black), 0, 1)
        board.setValueAt(Bishop(Black), 0, 2)
        board.setValueAt(Queen(Black), 0, 3)
        board.setValueAt(Bishop(Black), 0, 5)
        board.setValueAt(Knight(Black), 0, 6)
        board.setValueAt(Rook(Black), 0, 7)
        for (i in 0..<BoardModel.BOARD_SIZE) {
            board.setValueAt(Pawn(gameState, Black), 1, i)
        }

        whiteKingPosition = KingPosition(King(White), 7, 4)
        board.setValueAt(whiteKingPosition.king, 7, 4)
        board.setValueAt(Rook(White), 7, 0)
        board.setValueAt(Knight(White), 7, 1)
        board.setValueAt(Bishop(White), 7, 2)
        board.setValueAt(Queen(White), 7, 3)
        board.setValueAt(King(White), 7, 4)
        board.setValueAt(Bishop(White), 7, 5)
        board.setValueAt(Knight(White), 7, 6)
        board.setValueAt(Rook(White), 7, 7)
        for (i in 0..<BoardModel.BOARD_SIZE) {
            board.setValueAt(Pawn(gameState, White), 6, i)
        }

        updatePossibleMoves()
    }

    private fun updatePossibleMoves() {
        for (i in 0..<BoardModel.BOARD_SIZE) {
            for (j in 0..<BoardModel.BOARD_SIZE) {
                board.getValueAt(i, j)?.updatePossibleMoves(board, i, j)
            }
        }
    }

    fun onSquareClicked(rank: Int, file: Int) {
        if (selectedPiece == null) selectPiece(rank, file)
        else tryMove(rank, file)
    }

    private fun selectPiece(rank: Int, file: Int) {
        val clickedPiece = board.getValueAt(rank, file) ?: return

        if (!canSelectPiece(clickedPiece.color)) return
        if (clickedPiece.possibleMoves.isEmpty()) return

        selectedPiece = clickedPiece
        selectedRank = rank
        selectedFile = file
        board.setHighlightType(HighlightType.Selected, selectedRank, selectedFile)
        markPossibleMoves()

        view.repaint()
    }

    private fun tryMove(rank: Int, file: Int) {
        if (rank == selectedRank && file == selectedFile) {
            clearCellSelection()
            return
        }

        var takenMove: PlayerMove? = null
        for (move in selectedPiece!!.possibleMoves) {
            if (rank == move.to.rank && file == move.to.file) {
                takenMove = move
                break
            }
        }
        if (takenMove == null) return // The move was illegal.
        processMove(takenMove)
    }

    private fun processMove(takenMove: PlayerMove) {
        board.setValueAt(null, selectedRank, selectedFile)
        board.setValueAt(selectedPiece, takenMove.to.rank, takenMove.to.file)
        gameState.getStateForPlayer(currentPlayer).lastMove = takenMove
        val selection = selectedPiece!!
        clearCellSelection()
        selection.onMoved(board, takenMove)
        updatePossibleMoves()
        switchPlayer()
    }

    private fun handleCheck() {
        // For each of the opponent's pieces, check if it attacks our king.
        TODO()
    }

    private fun switchPlayer() {
        currentPlayer = currentPlayer.opposite
    }

    fun clearCellSelection() {
        if (selectedPiece == null) return
        board.setHighlightType(HighlightType.None, selectedRank, selectedFile)
        selectedPiece = null
        selectedRank = UNSELECTED_INDEX
        selectedFile = UNSELECTED_INDEX
        clearPossibleMoves()
        view.repaint()
    }

    private fun canSelectPiece(pieceColor: PieceColor) = pieceColor == currentPlayer

    private fun markPossibleMoves() {
        if (selectedPiece == null) return
        for (move in selectedPiece!!.possibleMoves) {
            board.setMarkType(MarkType.PossibleMove, move.to.rank, move.to.file)
        }
    }

    private fun clearPossibleMoves() {
        for (i in 0..<BoardModel.BOARD_SIZE) {
            for (j in 0..<BoardModel.BOARD_SIZE) {
                board.setMarkType(MarkType.None, i, j)
            }
        }
    }
}
