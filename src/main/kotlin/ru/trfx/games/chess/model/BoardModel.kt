package ru.trfx.games.chess.model

import ru.trfx.games.chess.model.piece.Piece
import javax.swing.table.AbstractTableModel

/**
 * The BoardModel is a model which holds the state of a chess board.
 */
class BoardModel : AbstractTableModel() {
    companion object {
        /**
         * The number of squares in each rank or file.
         */
        const val BOARD_SIZE = 8
    }

    /**
     * The type of mark displayed in a square.
     */
    enum class MarkType {
        /**
         * Indicates that the mark is hidden.
         */
        None,

        /**
         * Indicates that the mark represents a legal move.
         */
        LegalMove,
    }

    /**
     * The type of highlight of a square.
     */
    enum class HighlightType {
        /**
         * Indicates that the square is not highlighted.
         */
        None,

        /**
         * Indicates that the square is currently selected.
         */
        Selected,

        /**
         * Indicates that the square represents a possible capture move.
         */
        PossibleCapture,

        /**
         * Indicates that the square is a part of the path of an attacking piece.
         */
        AttackPath,
    }

    /**
     * Represents a board square.
     *
     * @param piece The piece occupying this square.
     */
    private data class BoardSquare(
        var piece: Piece? = null,
        var markType: MarkType = MarkType.None,
        var highlightType: HighlightType = HighlightType.None
    )

    /**
     * An 8x8 array of squares on this board.
     */
    private val squares = Array(BOARD_SIZE) {
        Array(BOARD_SIZE) { BoardSquare() }
    }

    override fun getRowCount(): Int = BOARD_SIZE

    override fun getColumnCount(): Int = BOARD_SIZE

    override fun getValueAt(rank: Int, file: Int): Piece? = squares[rank][file].piece

    /**
     * Returns true if the given square is occupied by a piece.
     *
     * @param rank The rank of the square.
     * @param file The file of the square.
     * @return True if the square is occupied.
     */
    fun hasPieceAt(rank: Int, file: Int): Boolean = squares[rank][file].piece != null

    override fun setValueAt(piece: Any?, rank: Int, file: Int) {
        check(piece is Piece?) { "Only pieces can be put on the board." }
        squares[rank][file].piece = piece
    }
}
