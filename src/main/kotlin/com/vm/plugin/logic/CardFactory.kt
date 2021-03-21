package com.vm.plugin.logic

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.utils.JsonManager.fromJson
import com.vm.plugin.utils.JsonManager.getTypeToken
import com.vm.plugin.utils.JsonManager.toJson
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType
import java.util.*
import kotlin.collections.ArrayList

object CardFactory {

    val namespace = NamespacedKey(MoneyCardKotlin.instance, "MoneyCardData")

    fun MoneyCardData.getItem(itemAmount: Int = 1): ItemStack {
        return ItemStack(Material.PAPER, itemAmount).also {
            it.itemMeta = it.itemMeta?.apply {
                setDisplayName("TODO Card Name")
                lore = ArrayList<String>().apply {
                    add("TODO Card Lore")
                }
                persistentDataContainer.set(namespace, MoneyCardPersistentData(), this@getItem)
            }
        }
    }
}

data class MoneyCardData constructor(
    val fromPlayer: Pair<UUID, String>?,
    val toPlayer: Pair<UUID, String>?,
    val cash: Double
) {
    companion object {
        fun create(fromPlayer: Player? = null, toPlayer: Player? = null, cash: Double = 0.0): MoneyCardData {
            return MoneyCardData(
                if (fromPlayer != null) fromPlayer.uniqueId to fromPlayer.name else null,
                if (toPlayer != null) toPlayer.uniqueId to toPlayer.name else null,
                cash
            )
        }

        fun create(fromPlayer: Player? = null, toPlayer: Player? = null, cash: Int = 0): MoneyCardData {
            return create(
                fromPlayer,
                toPlayer,
                cash.toDouble()
            )
        }
    }
}

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
        return primitive.fromJson(getTypeToken())
    }
}
