package com.vm.plugin.minecraft.events

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.logic.Bank.deposit
import com.vm.plugin.logic.CardDetector.getCardInfo
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class CardChangeToMoneyEvent : Listener {
    init {
        MoneyCardKotlin.instance.let {
            it.server.pluginManager.registerEvents(this, it)
        }
    }

    @EventHandler
    fun onPlayerInteractEvent(e: PlayerInteractEvent) {
        if (e.hand == EquipmentSlot.OFF_HAND) {
            return
        }

        val p = e.player
        e.item?.let { item ->
            item.getCardInfo()?.let { info ->
                if (p.isSneaking) {
                    val amount = item.amount
                    item.amount = 0
                    p.deposit(info.cash * amount)
                } else {
                    item.amount -= 1
                    p.deposit(info.cash)
                }
            }
        }
    }
}