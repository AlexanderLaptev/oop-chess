package ru.trfx.games.chess.view

import ru.trfx.games.chess.Configuration
import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardModel.HighlightType
import ru.trfx.games.chess.model.BoardModel.MarkType
import ru.trfx.games.chess.model.BoardSquare
import ru.trfx.games.chess.model.piece.Piece
import ru.trfx.games.chess.model.piece.PieceColor
import ru.trfx.games.chess.util.ConfigHelper
import java.awt.Component
import javax.swing.JTable
import javax.swing.border.CompoundBorder
import javax.swing.border.MatteBorder
import javax.swing.table.TableCellRenderer

class BoardViewCellRenderer : TableCellRenderer {
    companion object {
        /**
         * The color of a black cell.
         */
        private val BLACK_CELL_COLOR = ConfigHelper.parseColor("cell.color.black")

        /**
         * The color of a white cell.
         */
        private val WHITE_CELL_COLOR = ConfigHelper.parseColor("cell.color.white")

        /**
         * The thickness of the cell border.
         */
        private val BORDER_THICKNESS = Configuration["cell.border.thickness"].toInt()

        /**
         * The color of the cell border.
         */
        private val BORDER_COLOR = ConfigHelper.parseColor("cell.border.color")

        /**
         * The default border used for cells which are neither in the last row nor in the last column.
         */
        private val DEFAULT_BORDER = MatteBorder(BORDER_THICKNESS, BORDER_THICKNESS, 0, 0, BORDER_COLOR)

        /**
         * An additional bottom border for cells in the last row.
         */
        private val BOTTOM_BORDER = MatteBorder(0, 0, BORDER_THICKNESS, 0, BORDER_COLOR)

        /**
         * An additional right border for cells in the last column.
         */
        private val RIGHT_BORDER = MatteBorder(0, 0, 0, BORDER_THICKNESS, BORDER_COLOR)

        /**
         * The color for a legal move mark.
         */
        private val MARK_LEGAL_MOVE_COLOR = ConfigHelper.parseColor("mark.color.legalMove")

        /**
         * The tint for a selected cell.
         */
        private val SELECTED_TINT_COLOR = ConfigHelper.parseColor("cell.tint.selected")

        /**
         * The tint for a cell with a possible capture.
         */
        private val POSSIBLE_CAPTURE_TINT_COLOR = MARK_LEGAL_MOVE_COLOR

        /**
         * The tint for a cell on an attacking piece's path.
         */
        private val ATTACK_PATH_TINT_COLOR = ConfigHelper.parseColor("cell.tint.attackPath")
    }

    /**
     * The label which is used for rendering the cell.
     */
    private val label = MarkedLabel()

    override fun getTableCellRendererComponent(
        table: JTable?,
        value: Any?,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ): Component? {
        if (table == null || table !is BoardView) return null
        if (value !is Piece?) return null

        val model = table.model as BoardModel
        val icon = model.getValueAt(row, column)?.icon
        icon?.resize(table.rowHeight, table.rowHeight) // The cells are already square, so rowHeight = columnWidth.

        with(label) {
            this.icon = icon
            background = getBackgroundColorForCell(row, column)
            configureTint(row, column, model)
            configureBorder(row, column)
            configureMark(row, column, model)
        }

        return label
    }

    /**
     * Configures the tint of the given cell.
     */
    private fun configureTint(row: Int, column: Int, model: BoardModel) {
        val markType = model.getMarkType(row, column)
        val piece = model.getValueAt(row, column)
        if (markType == MarkType.PossibleMove && piece != null) {
            label.tintColor = POSSIBLE_CAPTURE_TINT_COLOR
            return
        }

        label.tintColor = when (model.getHighlightType(row, column)) {
            HighlightType.None -> null
            HighlightType.Selected -> SELECTED_TINT_COLOR
            HighlightType.Attack -> ATTACK_PATH_TINT_COLOR
        }
    }

    /**
     * Configures the border of the given cell.
     */
    private fun configureBorder(row: Int, column: Int) {
        with(label) {
            border = DEFAULT_BORDER
            if (row == BoardModel.BOARD_SIZE - 1) border = CompoundBorder(border, BOTTOM_BORDER)
            if (column == BoardModel.BOARD_SIZE - 1) border = CompoundBorder(border, RIGHT_BORDER)
        }
    }

    /**
     * Configures the mark of the given cell.
     */
    private fun configureMark(row: Int, column: Int, model: BoardModel) {
        if (model.getValueAt(row, column) != null) {
            // Do not draw the mark if the square is not empty.
            label.markColor = null
            return
        }

        val markColor = when (model.getMarkType(row, column)) {
            MarkType.None -> null
            MarkType.PossibleMove -> MARK_LEGAL_MOVE_COLOR
        }

        label.markColor = markColor
    }

    private fun getBackgroundColorForCell(row: Int, column: Int) =
        when (BoardSquare.getColorForSquare(row, column)) {
            PieceColor.Black -> BLACK_CELL_COLOR
            PieceColor.White -> WHITE_CELL_COLOR
        }
}
