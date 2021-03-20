package com.vm.plugin.utils

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object JsonManager {

    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create()

    fun Any?.toJson(): String {
        return gson.toJson(this)
    }

    fun String?.fromJson(type: Type): Any {
        return gson.fromJson(this, type)
    }

    inline fun <reified T> getType(): Type {
        return object : TypeToken<T>() {}.type
    }

    init {
    }


    class JsonIO(private var fileName: String) {
        init {
            if (!fileName.contains(".json")) fileName += ".json"
            ResourceIO.saveResourceIfNotExists(fileName)
        }

        fun saveToJson(obj: Any) = ResourceIO.useWriter(fileName) { gson.toJson(obj, it) }

        fun <T> fromJson(cls: Class<T>): T = ResourceIO.useReader(fileName) { gson.fromJson(it, cls) }

    }
}