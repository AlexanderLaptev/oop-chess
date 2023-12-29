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

        /**
         * The last index of any rank or file.
         */
        const val LAST_INDEX = BOARD_SIZE - 1
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
         * Indicates that the mark represents a possible move.
         */
        PossibleMove,
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
         * Indicates that the square is either an attacking piece or a checked king.
         */
        Attack,
    }

    /**
     * Represents a board square with visual data.
     *
     * @param piece The piece occupying this square.
     */
    private data class Cell(
        var piece: Piece? = null,
        var markType: MarkType = MarkType.None,
        var highlightType: HighlightType = HighlightType.None
    )

    /**
     * An 8x8 array of squares on this board.
     */
    private val squares = Array(BOARD_SIZE) {
        Array(BOARD_SIZE) { Cell() }
    }

    init {
        clear()
    }

    /**
     * Clears the highlighting and marks.
     */
    fun clear() {
        forEachSquare { rank, file ->
            setValueAt(null, rank, file)
            setHighlightType(HighlightType.None, rank, file)
            setMarkType(MarkType.None, rank, file)
        }
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

    /**
     * Gets the highlight type of the given square.
     *
     * @param rank The rank of the square.
     * @param file The file of the square.
     * @return The highlight type of the square.
     */
    fun getHighlightType(rank: Int, file: Int): HighlightType = squares[rank][file].highlightType

    /**
     * Sets the highlight type for the given square.
     *
     * @param type The highlight type for the square.
     * @param rank The rank of the square.
     * @param file The file of the square.
     */
    fun setHighlightType(type: HighlightType, rank: Int, file: Int) {
        squares[rank][file].highlightType = type
        fireTableCellUpdated(rank, file)
    }

    /**
     * Sets the mark type for the given square.
     *
     * @param type The mark type for the square.
     * @param rank The rank of the square.
     * @param file The file of the square.
     */
    fun setMarkType(type: MarkType, rank: Int, file: Int) {
        squares[rank][file].markType = type
        fireTableCellUpdated(rank, file)
    }

    /**
     * Gets the mark type of the given square.
     *
     * @param rank The rank of the square.
     * @param file The file of the square.
     * @return The mark type of the square.
     */
    fun getMarkType(rank: Int, file: Int): MarkType = squares[rank][file].markType

    override fun setValueAt(piece: Any?, rank: Int, file: Int) {
        check(piece is Piece?) { "Only pieces can be put on the board." }
        squares[rank][file].piece = piece
        fireTableCellUpdated(rank, file)
    }

    override fun getColumnClass(columnIndex: Int): Class<*> {
        return Piece::class.java
    }

    /**
     * Performs the given action on each square of the board.
     *
     * @param action The action to perform.
     */
    inline fun forEachSquare(action: (rank: Int, file: Int) -> Unit) {
        for (i in 0..LAST_INDEX) {
            for (j in 0..LAST_INDEX) {
                action(i, j)
            }
        }
    }
}
