package com.vm.plugin.utils

import com.vm.plugin.MoneyCardKotlin
import java.io.*

object ResourceIO {

    private val plugin = MoneyCardKotlin.instance

    fun saveResourceIfNotExists(fileName: String): Boolean {
        if (fileName == "") {
            throw IllegalArgumentException("ResourcePath cannot be null or empty")
        }

        val finalFileName = fileName.replace("\\", "/").let {
            if (it.startsWith("/")) it else "/$it"
        }

        val outFile = getFile(finalFileName).apply {
            if (exists()) {
                return false
            }
        }

        finalFileName.lastIndexOf('/').let {
            File(plugin.dataFolder, finalFileName.substring(0, if (it >= 0) it else 0)).run {
                if (!exists()) {
                    mkdirs()
                }
            }
        }

        val reader = plugin.javaClass.getResourceAsStream(finalFileName)?.reader()
        reader ?: run {
            if (outFile.createNewFile() && !outFile.exists()) {
                throw InternalError("Cannot create a new file in resources path")
            }
            return true
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

        return true
    }

    fun <R> useReader(fileName: String, block: (Reader) -> R): R = getReader(fileName).use(block)

    fun <R> useWriter(fileName: String, block: (Writer) -> R): R = getWriter(fileName).use(block)

    private fun getReader(fileName: String): Reader = FileReader(getFile(fileName))

    private fun getWriter(fileName: String): Writer = FileWriter(getFile(fileName))

    fun getFile(fileName: String): File = File(plugin.dataFolder, fileName)
}