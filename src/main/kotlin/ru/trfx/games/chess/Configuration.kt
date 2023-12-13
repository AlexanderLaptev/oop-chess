package ru.trfx.games.chess

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*

/**
 * The global access point to the default configuration options.
 */
object Configuration {
    /**
     * The path to the default properties resource file.
     */
    private const val DEFAULT_PROPERTIES_RESOURCE_PATH = "/default.properties"

    /**
     * The properties loaded by this object.
     */
    private val properties = loadProperties()

    /**
     * Gets the corresponding configuration option.
     *
     * @param option The name of the option to retrieve.
     * @return The corresponding configuration option.
     */
    operator fun get(option: String): String = properties.getProperty(option)!!

    /**
     * Loads the default properties from the resource file.
     */
    private fun loadProperties(): Properties {
        val resourcePath = Configuration::class.java.getResource(DEFAULT_PROPERTIES_RESOURCE_PATH)
        check(resourcePath != null) { "Cannot load default configuration." }
        val file = File(resourcePath.file)
        return Properties().apply {
            load(BufferedReader(FileReader(file)))
        }
    }
}
