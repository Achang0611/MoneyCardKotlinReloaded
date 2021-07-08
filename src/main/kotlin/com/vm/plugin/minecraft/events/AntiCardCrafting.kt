package com.vm.plugin.minecraft.events

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.logic.CardDetector.isCard
import com.vm.plugin.minecraft.Permissions
import com.vm.plugin.minecraft.RequirePermissible
import com.vm.plugin.minecraft.Sender.hasPermission
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack

class AntiCardCrafting : Listener, RequirePermissible {

    override val required = Permissions.ActCraftingBypass

    init {
        MoneyCardKotlin.instance.let {
            it.server.pluginManager.registerEvents(this, it)
        }
    }

    @EventHandler
    fun onPrepareItemCraft(e: PrepareItemCraftEvent) {
        val p = e.view.player

        if (p.hasPermission(required)) {
            return
        }


        e.inventory.matrix.forEach {
            it // 因為kotlin的bug 沒辦法偵測他有可能是null
            if (it.isCard()) {
                e.inventory.result = ItemStack(Material.AIR)
            }
        }
    }
}