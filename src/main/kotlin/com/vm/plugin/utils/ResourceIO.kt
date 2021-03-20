package com.vm.plugin.utils

import com.vm.plugin.MoneyCardKotlin
import java.io.*

object ResourceIO {

    private val plugin = MoneyCardKotlin.instance

    fun saveResourceIfNotExists(fileNameUnchecked: String) {
        if (fileNameUnchecked == "") {
            throw IllegalArgumentException("ResourcePath cannot be null or empty")
        }

        val fileName =
            if (!fileNameUnchecked.startsWith("/") || !fileNameUnchecked.startsWith("\\")) "/$fileNameUnchecked"
            else fileNameUnchecked

        val reader = plugin.javaClass.getResourceAsStream(fileName).reader()

        val outFile = File(plugin.dataFolder, fileName).apply {
            if (exists()) {
                return
            }
        }

        fileName.lastIndexOf('\\').let {
            File(plugin.dataFolder, fileName.substring(0, if (it >= 0) it else 0)).run {
                if (!exists()) {
                    mkdirs()
                }
            }
        }


        reader.use {
            FileWriter(outFile).use { out ->
                val bufferSize = 8192
                val buffer = CharArray(bufferSize)
                var nRead: Int

                while (it.read(buffer, 0, bufferSize).also { nRead = it } >= 0) {
                    out.write(buffer, 0, nRead)
                }
            }
        }
    }

    fun <R> useReader(fileName: String, block: (Reader) -> R): R = getReader(fileName).use(block)

    fun <R> useWriter(fileName: String, block: (Writer) -> R): R = getWriter(fileName).use(block)

    private fun getReader(fileName: String): Reader = FileReader(File(plugin.dataFolder, fileName))

    private fun getWriter(fileName: String): Writer = FileWriter(File(plugin.dataFolder, fileName))
}