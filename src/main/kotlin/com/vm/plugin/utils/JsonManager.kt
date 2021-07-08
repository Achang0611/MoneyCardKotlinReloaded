package com.vm.plugin.utils

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


object JsonManager {

    private val gson by lazy {
        GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create()
    }

    fun Any?.toJson(): String = gson.toJson(this)


    fun <T> String?.fromJson(type: TypeToken<T>): T = gson.fromJson(this, type.type)


    inline fun <reified T> getTypeToken(): TypeToken<T> = object : TypeToken<T>() {}


    object Message {
        private val message by lazy { JsonIO("message.json") }

        private fun getStruct(): Map<String, Map<String, String>> =
            message.fromJson(getTypeToken())

        fun getValue(path: String): Pair<String, Error> {
            val keys = path.split(".")
            if (keys.size != 2) {
                return "" to Error.jsonKeyNotFound(path)
            }

            val result = getStruct()[keys[0]]?.get(keys[1])
            return if (result != null) {
                result to Error.notError()
            } else "" to Error.jsonKeyNotFound(path)
        }
    }

    object CreateCardRules {
        private val createCardRules by lazy { JsonIO("create_card_rules.json") }
        val rules: Rules = createCardRules.fromJson(getTypeToken())

        data class AmountLimit(val minimum: Int, val maximum: Int)
        data class CashLimit(val minimum: Double, val maximum: Double)
        data class Rules(val amount: AmountLimit, val cash: CashLimit)
    }

    val cardItemSetting by lazy { JsonIO("card_item_setting.json") }

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