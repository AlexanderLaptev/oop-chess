package ru.trfx.games.chess.util

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardSquare
import ru.trfx.games.chess.model.piece.PieceColor

/**
 * A helper object containing common methods used by various chess pieces.
 */
object PieceHelper {
    /**
     * Scans the board in the given direction and adds valid moves to the accumulator.
     *
     * @param pieceColor The color of the piece which is scanning the board.
     * @param board The board model.
     * @param position The position of the piece.
     * @param deltaRank The change in rank in one step.
     * @param deltaFile The change in file in one step.
     * @param accumulator A mutable collection of moves for the scanning piece.
     */
    fun scanDirection(
        pieceColor: PieceColor,
        board: BoardModel,
        position: BoardSquare,
        deltaRank: Int,
        deltaFile: Int,
        accumulator: MutableCollection<BoardSquare>,
    ) {
        var rank = position.rank
        var file = position.file

        while (true) {
            rank += deltaRank
            file += deltaFile

            if (!BoardSquare.areCoordinatesValid(rank, file)) return
            val otherPiece = board.getValueAt(rank, file)
            if (otherPiece == null) accumulator += BoardSquare(rank, file)
            else if (otherPiece.color != pieceColor) {
                accumulator += BoardSquare(rank, file)
                return
            } else return
        }
    }

    /**
     * Adds the given move to the accumulator if the target square is empty or there is an enemy piece there.
     *
     * @param pieceColor The color of the moving piece.
     * @param board The board model.
     * @param rank The rank of the target cell.
     * @param file The file of the target cell.
     * @param accumulator A mutable collection of possible moves for the moving piece.
     */
    fun addMoveIfPossible(
        pieceColor: PieceColor,
        board: BoardModel,
        rank: Int,
        file: Int,
        accumulator: MutableCollection<BoardSquare>
    ) {
        if (!BoardSquare.areCoordinatesValid(rank, file)) return
        val piece = board.getValueAt(rank, file)
        if (piece == null || piece.color == pieceColor) return
        accumulator += BoardSquare(rank, file)
    }
}
