package com.vm.plugin.logic

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.utils.JsonManager
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import java.util.*

object CardFactory {

    val namespace = NamespacedKey(MoneyCardKotlin.instance, "MoneyCardData")
    private val setting = JsonManager.cardItemSetting.fromJson(JsonManager.getTypeToken<CardItemSetting>())

    fun MoneyCardData.getItem(itemAmount: Int = 1): ItemStack {
        return ItemStack(setting.material, itemAmount).also {
            it.itemMeta = it.itemMeta?.apply {
                setDisplayName(setting.name)
                lore = setting.lore
                persistentDataContainer.set(namespace, MoneyCardPersistentData(), this@getItem)
            }
        }
    }

    data class CardItemSetting(val material: Material, val name: String, val lore: List<String>)
}
