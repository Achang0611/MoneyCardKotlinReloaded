package com.vm.plugin.minecraft.events

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.logic.CardDetector.isCard
import com.vm.plugin.minecraft.Permissions
import com.vm.plugin.minecraft.RequirePermissible
import com.vm.plugin.minecraft.Sender.hasPermission
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

class AntiCardTrading : Listener, RequirePermissible {
    override val required: Permissions = Permissions.ActTradingBypass

    init {
        MoneyCardKotlin.instance.let {
            it.server.pluginManager.registerEvents(this, it)
        }
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val p = e.view.player

        if (p.hasPermission(required)) {
            return
        }

        if (e.click == ClickType.NUMBER_KEY) {
            val movedItemStack = p.inventory.getItem(e.hotbarButton) ?: return
            if (!movedItemStack.isCard()) {
                return
            }
        } else {
            val clickedItemStack = e.currentItem ?: return
            if (!clickedItemStack.isCard()) {
                return
            }
        }

        val topInventory = e.view.topInventory
        if (topInventory.type == InventoryType.MERCHANT) {
            e.isCancelled = true
        }
    }
}