package com.vm.plugin.minecraft.events

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.logic.CardConverter.cardToMoney
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
        e.item?.also { item ->
            item.getCardInfo()?.also { info ->
                p.cardToMoney(item, info, p.isSneaking).errMsg?.let {
                    TODO(it)
                }
            }
        }
    }
}