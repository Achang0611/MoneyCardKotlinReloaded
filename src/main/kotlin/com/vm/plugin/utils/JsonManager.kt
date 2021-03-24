package com.vm.plugin.utils

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


object JsonManager {

    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create()

    fun Any?.toJson(): String {
        return gson.toJson(this)
    }

    fun <T> String?.fromJson(type: TypeToken<T>): T {
        return gson.fromJson(this, type.type)
    }

    inline fun <reified T> getTypeToken(): TypeToken<T> {
        return object : TypeToken<T>() {}
    }


    val message = JsonIO("message.json")
    val cardItemSetting = JsonIO("card_item_setting.json")

    class JsonIO(private var fileName: String) {
        init {
            if (!fileName.contains(".json")) fileName += ".json"
            ResourceIO.saveResourceIfNotExists(fileName)
        }

        fun saveToJson(obj: Any) = ResourceIO.useWriter(fileName) { gson.toJson(obj, it) }

        fun <T> fromJson(typeToken: TypeToken<T>): T =
            ResourceIO.useReader(fileName) { gson.fromJson(it, typeToken.type) }

    }
}