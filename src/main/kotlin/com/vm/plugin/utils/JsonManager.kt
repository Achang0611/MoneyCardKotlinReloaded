package com.vm.plugin.utils

import com.google.gson.GsonBuilder
import org.bukkit.plugin.java.JavaPlugin
import java.io.Reader
import java.io.Writer

class JsonManager(val plugin: JavaPlugin) {
    inner class JsonIO(private var fileName: String, val replace: Boolean) {
        private val gson = GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create()

        private val reader: Reader
            get() = ResourceIO.getReader(fileName)!!


        private val writer: Writer
            get() = ResourceIO.getWriter(fileName)!!

        init {
            if (!fileName.contains(".json")) fileName += ".json"
            ResourceIO.saveResourceIfNotExists(fileName)
        }

        fun saveToJson(obj: Any) = writer.use { gson.toJson(obj, it) }


        fun <T> fromJson(cls: Class<T>): T = reader.use { return gson.fromJson(it, cls) }

    }
}