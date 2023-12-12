package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardModel
import ru.trfx.games.chess.model.BoardSquare
import ru.trfx.games.chess.model.PlayerMove
import ru.trfx.games.chess.view.ResizableIcon
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

/**
 * Represents a chess piece.
 *
 * @param color The color of the piece.
 * @param imageChar The character used to look up the icon for the piece.
 */
abstract class Piece(
    val color: PieceColor,
    imageChar: Char,
) {
    companion object {
        /**
         * The base path for piece image resources relative to the resources root.
         */
        private const val BASE_IMAGE_PATH = "/images/pieces/"

        /**
         * The extension of the piece images.
         */
        private const val IMAGE_EXTENSION = ".png"
    }

    /**
     * The icon used to display this piece on the board.
     */
    val icon: ResizableIcon = ResizableIcon(getPieceImage(imageChar))

    /**
     * Returns an immutable collection of the squares this piece can move to.
     *
     * @param board The board model.
     * @param from The initial position of the piece.
     * @param to The target position of the piece.
     * @return An immutable collection of legal moves of this piece.
     */
    abstract fun getLegalMoves(board: BoardModel, from: BoardSquare, to: BoardSquare): Collection<BoardSquare>

    /**
     * Called when the piece was successfully moved by the player.
     *
     * @param board The board model.
     * @param move The move taken by the player.
     */
    abstract fun onMoved(board: BoardModel, move: PlayerMove)

    /**
     * Reads a [BufferedImage] resource of the given name from the piece image directory.
     *
     * @param imageChar The character used to construct the full name of the image file.
     * @see BASE_IMAGE_PATH
     * @see IMAGE_EXTENSION
     */
    private fun getPieceImage(imageChar: Char): BufferedImage {
        val colorChar = when (color) {
            PieceColor.Black -> 'b'
            PieceColor.White -> 'w'
        }
        val imageName = "${colorChar}${imageChar}"
        val url = this::class.java.getResource(BASE_IMAGE_PATH + imageName + IMAGE_EXTENSION)
        return ImageIO.read(url)
    }
}
