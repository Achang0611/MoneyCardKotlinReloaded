package com.vm.plugin.minecraft

import org.bukkit.Material
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

object InventoryEditor {
    infix fun InventoryHolder.addItemStackSafely(itemStack: ItemStack): Boolean {
        var slotLeft = 0
        this.inventory.storageContents.forEach {
            if (it == null || it.type == Material.AIR || it.isSimilar(itemStack)) {
                slotLeft++
            }
        }
        val slotNeeded = itemStack.amount / itemStack.maxStackSize.toFloat()

        return if (slotLeft >= slotNeeded) {
            this.inventory.addItem(itemStack)
            true
        } else false
    }
}