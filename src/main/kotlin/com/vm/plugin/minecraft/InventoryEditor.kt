package com.vm.plugin.minecraft

import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

infix fun InventoryHolder.addItemStackSafely(itemStack: ItemStack): Boolean {
    val slotLeft = this.inventory.maxStackSize - this.inventory.size
    val slotNeeded = itemStack.amount / 64f

    return if (slotLeft >= slotNeeded) {
        this.inventory.addItem(itemStack)
        true
    } else false
}