package ru.trfx.games.chess.controller

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardModel.HighlightType
import ru.trfx.games.chess.model.BoardModel.MarkType
import ru.trfx.games.chess.model.BoardSquare
import ru.trfx.games.chess.model.GameState
import ru.trfx.games.chess.model.PlayerMove
import ru.trfx.games.chess.model.piece.*
import ru.trfx.games.chess.model.piece.PieceColor.Black
import ru.trfx.games.chess.model.piece.PieceColor.White
import ru.trfx.games.chess.view.BoardView
import javax.swing.JLabel

/**
 * The `GameController` changes the state of the game, moves the pieces on the board
 * and enforces other game rules.
 *
 * @param board The board model.
 * @param view The board view used to display the current game state.
 */
class GameController(val board: BoardModel, val view: BoardView) {
    var statusLabel: JLabel? = null
        set(value) {
            field = value
            updateStatusLabel()
        }

    /**
     * The currently selected square, or null if nothing is selected.
     */
    private var selectedSquare: BoardSquare? = null

    /**
     * The color of the player who is making a move.
     */
    private var currentPlayerColor = White

    /**
     * The state of the game.
     */
    private val gameState = GameState()

    init {
        initializeBoard()
    }

    private fun initializeBoard() {
        with(board) {
            // Black pieces.
            setValueAt(Rook(Black), 0, 0)
            setValueAt(Knight(Black), 0, 1)
            setValueAt(Bishop(Black), 0, 2)
            setValueAt(Queen(Black), 0, 3)
            setValueAt(King(Black), 0, 4)
            setValueAt(Bishop(Black), 0, 5)
            setValueAt(Knight(Black), 0, 6)
            setValueAt(Rook(Black), 0, 7)
            for (i in 0..BoardModel.LAST_INDEX) {
                setValueAt(Pawn(gameState, Black), 1, i)
            }

            // White pieces.
            setValueAt(Rook(White), 7, 0)
            setValueAt(Knight(White), 7, 1)
            setValueAt(Bishop(White), 7, 2)
            setValueAt(Queen(White), 7, 3)
            setValueAt(King(White), 7, 4)
            setValueAt(Bishop(White), 7, 5)
            setValueAt(Knight(White), 7, 6)
            setValueAt(Rook(White), 7, 7)
            for (i in 0..BoardModel.LAST_INDEX) {
                setValueAt(Pawn(gameState, White), 6, i)
            }
        }
        updatePossibleMoves()
        updateStatusLabel()
    }

    private fun updatePossibleMoves() {
        board.forEachSquare { rank, file ->
            board.getValueAt(rank, file)?.updatePossibleMoves(board, rank, file)
        }
    }

    /**
     * Called when the player clicks a square.
     *
     * @param rank The rank of the square.
     * @param file The file of the square.
     */
    fun onSquareClicked(rank: Int, file: Int) {
        val clickedSquare = BoardSquare(rank, file)
        if (selectedSquare == null) requestSelection(clickedSquare)
        else requestMove(clickedSquare)
    }

    /**
     * Requests the selection for a clicked square. [canSelectPiece]
     * is called to determine whether the square can be selected.
     */
    private fun requestSelection(clickedSquare: BoardSquare) {
        val clickedPiece = board.getValueAt(
            clickedSquare.rank,
            clickedSquare.file
        ) ?: return // Cannot select an empty square.
        if (!canSelectPiece(clickedPiece)) return

        selectSquare(clickedSquare, clickedPiece)
    }

    private fun selectSquare(clickedSquare: BoardSquare, clickedPiece: Piece) {
        selectedSquare = clickedSquare

        board.setHighlightType(HighlightType.Selected, clickedSquare.rank, clickedSquare.file)
        markPossibleMoves(clickedPiece)
        view.repaint()
    }

    /**
     * Marks the squares the given piece can move to. Called entirely on the AWT event dispatching thread.
     *
     * @param piece The moving piece.
     */
    private fun markPossibleMoves(piece: Piece) {
        for (move in piece.possibleMoves) {
            board.setMarkType(MarkType.PossibleMove, move.to.rank, move.to.file)
        }
    }

    private fun canSelectPiece(piece: Piece) =
        piece.color == currentPlayerColor && piece.possibleMoves.isNotEmpty()

    private fun requestMove(clickedSquare: BoardSquare) {
        if (clickedSquare == selectedSquare) {
            clearSelection()
        } else {
            val selectedSquare = selectedSquare!!
            val selectedPiece = board.getValueAt(selectedSquare.rank, selectedSquare.file)!!
            for (move in selectedPiece.possibleMoves) {
                if (clickedSquare == move.to) {
                    processMove(selectedPiece, move)
                    return
                }
            }
        }
    }

    private fun processMove(piece: Piece, takenMove: PlayerMove) {
        movePiece(selectedSquare!!, piece, takenMove)
        updatePossibleMoves()
        clearSelection()
        checkKingInCheck()
        switchPlayer()

        updateStatusLabel()
        view.repaint()
    }

    private fun updateStatusLabel() {
        statusLabel?.text = when (currentPlayerColor) {
            Black -> "Black moves"
            White -> "White moves"
        }
    }

    private fun movePiece(selectedSquare: BoardSquare, piece: Piece, takenMove: PlayerMove) {
        gameState.getStateForPlayer(currentPlayerColor).lastMove = takenMove
        with(board) {
            setValueAt(null, selectedSquare.rank, selectedSquare.file)
            setValueAt(piece, takenMove.to.rank, takenMove.to.file)
        }
        piece.onMoved(board, takenMove)
    }

    private fun checkKingInCheck() {
        val kingSquare = findEnemyKingSquare()
        board.forEachSquare { rank, file ->
            if (board.getHighlightType(rank, file) == HighlightType.Attack) {
                board.setHighlightType(HighlightType.None, rank, file)
            }

            val piece = board.getValueAt(rank, file)
            if (piece == null || piece.color != currentPlayerColor) return@forEachSquare
            val moves = piece.possibleMoves
            var isInCheck = false
            for (move in moves) {
                if (kingSquare == move.to) {
                    isInCheck = true
                    board.setHighlightType(HighlightType.Attack, rank, file)
                }
            }
            if (isInCheck) checkMate(board.getValueAt(kingSquare.rank, kingSquare.file)!!.possibleMoves)
        }
    }

    private fun checkMate(kingMoves: Collection<PlayerMove>) {
        if (kingMoves.isEmpty()) {
            MessageBoxProxy.showCheckmateMessage()
        }
    }

    private fun findEnemyKingSquare(): BoardSquare {
        var square: BoardSquare?
        board.forEachSquare { rank, file ->
            val piece = board.getValueAt(rank, file)
            if (piece != null && piece is King && piece.color != currentPlayerColor) {
                square = BoardSquare(rank, file)
                return square!!
            }
        }
        error("Could not find the enemy king.")
    }

    private fun switchPlayer() {
        currentPlayerColor = currentPlayerColor.opposite
    }

    /**
     * Clears the selection. Clearing the selection is accomplished by:
     * - Resetting the selected square.
     * - Removing the highlighting from the previously selected cell.
     * - Removing possible move marks from the board.
     */
    fun clearSelection() {
        if (selectedSquare == null) return
        val selectedSquare = selectedSquare!!
        this.selectedSquare = null
        board.setHighlightType(HighlightType.None, selectedSquare.rank, selectedSquare.file)
        removeMarks()
        view.repaint()
    }

    /**
     * Removes all marks from the board.
     *
     * **Note:** This method interacts with the model directly,
     * so it should be called on the AWT event dispatching thread.
     */
    private fun removeMarks() {
        board.forEachSquare { rank, file -> board.setMarkType(MarkType.None, rank, file) }
    }
}
