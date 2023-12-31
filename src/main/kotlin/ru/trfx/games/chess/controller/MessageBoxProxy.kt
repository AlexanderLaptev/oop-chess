package ru.trfx.games.chess.controller

import ru.trfx.games.chess.model.piece.*
import javax.swing.JOptionPane

object MessageBoxProxy {
    fun showPromotionDialog(pieceColor: PieceColor): Piece {
        val result = JOptionPane.showOptionDialog(
            null,
            "Select which piece to promote this pawn to:",
            "Pawn Promotion",
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            arrayOf("Queen", "Rook", "Bishop", "Knight"),
            0
        )

        return when (result) {
            0 -> Queen(pieceColor)
            1 -> Rook(pieceColor, false)
            2 -> Bishop(pieceColor)
            3 -> Knight(pieceColor)
            else -> error("Unexpected option index: $result.")
        }
    }

    fun showCheckmateMessage() {
        JOptionPane.showMessageDialog(
            null,
            "Checkmate!",
            "Checkmate",
            JOptionPane.OK_OPTION
        )
    }
}
