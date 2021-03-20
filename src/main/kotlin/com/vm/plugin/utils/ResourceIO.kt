package com.vm.plugin.utils

import com.vm.plugin.MoneyCardKotlin
import java.io.*
import java.net.URLConnection

object ResourceIO {

    private val plugin = MoneyCardKotlin.instance

    fun saveResourceIfNotExists(fileName: String) {
        if (fileName == "") {
            throw IllegalArgumentException("ResourcePath cannot be null or empty")
        }

        val reader = getReader(fileName)
            ?: throw IllegalArgumentException("The embedded resource '$fileName' cannot be found in jar")

        val outFile = File(plugin.dataFolder, fileName)
        if (outFile.exists()) {
            return
        }

        val lastIndex = fileName.lastIndexOf('\\')
        val outDir = File(plugin.dataFolder, fileName.substring(0, if (lastIndex >= 0) lastIndex else 0))
        if (!outDir.exists()) {
            outDir.mkdirs()
        }

        try {
            val out = FileWriter(outFile)

            val bufferSize = 8192
            val buffer = CharArray(bufferSize)
            var nRead: Int
            while (reader.read(buffer, 0, bufferSize).also { nRead = it } >= 0) {
                out.write(buffer, 0, nRead)
            }

            out.close()
            reader.close()
        } catch (ex: IOException) {
            throw ex
        }
    }

    private fun getURLConnection(fileName: String): URLConnection? {
        return plugin.javaClass.classLoader.getResource(fileName)?.openConnection()
    }

    fun getReader(fileName: String): BufferedReader? {
        return BufferedReader(getURLConnection(fileName)?.getInputStream()?.reader() ?: return null)
    }

    fun getWriter(fileName: String): Writer? {
        return BufferedWriter(getURLConnection(fileName)?.getOutputStream()?.writer() ?: return null)
    }
}