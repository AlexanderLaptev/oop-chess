package ru.trfx.games.chess.util

import java.awt.RenderingHints
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

        val result = BufferedImage(newWidth, newHeight, image.type)
        val g2d = result.createGraphics()
        try {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
            g2d.drawImage(image, 0, 0, newWidth, newHeight, null)
        } finally {
            g2d.dispose()
        }
        return result
    }
}
