package ru.trfx.games.chess.util

import net.coobird.thumbnailator.Thumbnails
import java.awt.image.BufferedImage

/**
 * Various helper methods for dealing with images.
 */
object ImageHelper {
    /**
     * Creates a copy of the given image resized to the specified dimensions. If the given image already has
     * the specified dimensions, no resizing is performed, and the original image is returned.
     *
     * @param image The image to resize
     * @param newWidth The width of the resized image.
     * @param newHeight The height of the resized image.
     * @return The resized copy of the original image or the original image if it already has the right size.
     */
    fun resize(image: BufferedImage, newWidth: Int, newHeight: Int): BufferedImage {
        if (image.width == newWidth && image.height == newHeight) return image
        return Thumbnails.of(image).size(newWidth, newHeight).asBufferedImage()
    }
}
