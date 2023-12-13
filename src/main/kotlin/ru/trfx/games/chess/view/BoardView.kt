package ru.trfx.games.chess.view

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.piece.Piece
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.HierarchyEvent
import java.awt.event.HierarchyListener
import javax.swing.InputMap
import javax.swing.JTable
import javax.swing.ListSelectionModel
import kotlin.math.min

class BoardView(model: BoardModel) : JTable(model) {
    /**
     * Listens for parent changes and adds/removes the resizeListener from them as needed.
     */
    private val hierarchyListener = object : HierarchyListener {
        override fun hierarchyChanged(e: HierarchyEvent?) {
            if (!isInterestedIn(e)) return
            parent?.removeComponentListener(resizeListener)
            e!!.changedParent?.addComponentListener(resizeListener)
        }

        private fun isInterestedIn(e: HierarchyEvent?): Boolean {
            if (e == null) return false
            if (e.id != HierarchyEvent.HIERARCHY_CHANGED) return false
            if (e.changeFlags and HierarchyEvent.PARENT_CHANGED.toLong() != 0L) return false
            if (e.changed != this@BoardView) return false
            return true
        }
    }

    /**
     * Listens for parent resizes and resizes the cells accordingly.
     */
    private val resizeListener = object : ComponentAdapter() {
        override fun componentResized(e: ComponentEvent?) {
            val insets = parent.insets
            val minDimension = min(
                parent.height - insets.top - insets.bottom,
                parent.width - insets.left - insets.right
            )
            val cellSize = minDimension / BoardModel.BOARD_SIZE
            resizeCells(cellSize)
        }
    }

    init {
        addHierarchyListener(hierarchyListener)
        setUpRendering()
        disableManualSelection()
    }

    private fun setUpRendering() {
        setDefaultRenderer(Piece::class.java, BoardViewCellRenderer())
        intercellSpacing = Dimension()
    }

    /**
     * Prevent the default cell selection behavior.
     *
     * **Note:** a call to [updateUI] will restore the default
     * behavior, as the UI also sets up the default listeners.
     */
    private fun disableManualSelection() {
        rowSelectionAllowed = true
        columnSelectionAllowed = true
        isFocusable = false
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION)

        // Prevent the default cell selection behavior.
        // NB: a call to updateUI() will restore the default behavior.
        for (listener in mouseListeners) removeMouseListener(listener)
        for (listener in mouseMotionListeners) removeMouseMotionListener(listener)
        for (listener in keyListeners) removeKeyListener(listener)
        setInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, InputMap()) // Disable keyboard controls.
    }

    /**
     * Resizes the table cells to a square of the given size.
     *
     * @param newSize The new size of the cell.
     */
    private fun resizeCells(newSize: Int) {
        setRowHeight(newSize)
        for (column in columnModel.columns) {
            with(column) {
                preferredWidth = newSize
            }
        }
    }
}
