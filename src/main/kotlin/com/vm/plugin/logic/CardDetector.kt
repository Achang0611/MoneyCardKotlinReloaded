package com.vm.plugin.logic

import org.bukkit.inventory.ItemStack

object CardDetector {
    fun ItemStack.getCardInfo(): MoneyCardData? {
        return if (this.isCard()) {
            this.itemMeta!!.run {
                persistentDataContainer.get(CardFactory.namespace, MoneyCardPersistentData())
            }
        } else null
    }

    private fun ItemStack.isCard(): Boolean {
        return this.itemMeta?.run {
            persistentDataContainer.has(CardFactory.namespace, MoneyCardPersistentData())
        } ?: false
    }
}