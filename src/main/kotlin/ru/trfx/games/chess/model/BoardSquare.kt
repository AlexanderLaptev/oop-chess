package ru.trfx.games.chess.model

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
    }

    init {
        require(isCoordinateValid(rank)) { "Invalid rank of the square: $rank." }
        require(isCoordinateValid(file)) { "Invalid file of the square: $file." }
    }
}
