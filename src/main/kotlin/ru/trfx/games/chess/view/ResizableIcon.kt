package ru.trfx.games.chess.view

import ru.trfx.games.chess.util.ImageHelper
import java.awt.image.BufferedImage
import javax.swing.ImageIcon

/**
 * A resizable image icon. Stores the original image and returns its resized copy when asked.
 * The original image is not modified in any way. Call the [resize] method to actually resize the icon.
 *
 * @param originalImage The original image with original dimensions. This image will not be changed.
 */
class ResizableIcon(val originalImage: BufferedImage) : ImageIcon() {
    init {
        image = originalImage
    }

    /**
     * Resizes the icon to the specified dimensions.
     *
     * @param newWidth The width of the resized icon.
     * @param newHeight The height of the resized icon.
     */
    fun resize(newWidth: Int, newHeight: Int) {
        image = ImageHelper.resize(originalImage, newWidth, newHeight)
    }
}
