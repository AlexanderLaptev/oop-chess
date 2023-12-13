package ru.trfx.games.chess.model

import ru.trfx.games.chess.model.piece.PieceColor

/**
 * A square on a chess board. The coordinates are guaranteed to be valid.
 *
 * @param rank The rank of the square.
 * @param file The file of the square.
 */
data class BoardSquare(
    val rank: Int,
    val file: Int
) {
    companion object {
        /**
         * Returns true if the given rank/file is valid.
         *
         * @param coordinate The coordinate to check.
         * @return True if the coordinate is valid.
         */
        fun isCoordinateValid(coordinate: Int): Boolean = coordinate in 0..<BoardModel.BOARD_SIZE

        /**
         * Returns true if both the given coordinates are valid.
         *
         * @param rank The rank of the square.
         * @param file The file of the square.
         * @return True if both the given coordinates are valid.
         */
        fun areCoordinatesValid(rank: Int, file: Int): Boolean = isCoordinateValid(rank) && isCoordinateValid(file)

        /**
         * Returns the color of the given square.
         *
         * @param rank The rank of the square.
         * @param file The file of the square.
         * @return The color of the square.
         */
        fun getColorForSquare(rank: Int, file: Int): PieceColor =
            if ((rank + file) % 2 == 0) PieceColor.White else PieceColor.Black
    }

    init {
        require(isCoordinateValid(rank)) { "Invalid rank of the square: $rank." }
        require(isCoordinateValid(file)) { "Invalid file of the square: $file." }
    }
}
