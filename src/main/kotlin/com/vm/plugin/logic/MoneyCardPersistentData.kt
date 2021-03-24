package com.vm.plugin.logic

import com.vm.plugin.utils.JsonManager
import com.vm.plugin.utils.JsonManager.fromJson
import com.vm.plugin.utils.JsonManager.toJson
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

class MoneyCardPersistentData
    : PersistentDataType<String, MoneyCardData> {
    override fun getPrimitiveType(): Class<String> {
        return String::class.java
    }

    override fun getComplexType(): Class<MoneyCardData> {
        return MoneyCardData::class.java
    }

    override fun toPrimitive(complex: MoneyCardData, context: PersistentDataAdapterContext): String {
        return complex.toJson()
    }

    override fun fromPrimitive(primitive: String, context: PersistentDataAdapterContext): MoneyCardData {
        return primitive.fromJson(JsonManager.getTypeToken())
    }
}