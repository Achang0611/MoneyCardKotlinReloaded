package com.vm.plugin.logic

import com.vm.plugin.MoneyCardKotlin
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import java.util.*

object CardFactory {

    val namespace = NamespacedKey(MoneyCardKotlin.instance, "MoneyCardData")

    fun MoneyCardData.getItem(itemAmount: Int = 1): ItemStack {
        val setting = ItemSettingFormatter.formattedSetting(this.cash)
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
