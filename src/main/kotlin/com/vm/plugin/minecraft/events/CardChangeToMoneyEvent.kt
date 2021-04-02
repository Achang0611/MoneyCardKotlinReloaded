package com.vm.plugin.minecraft.events

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.logic.CardConverter.cardToMoney
import com.vm.plugin.logic.CardDetector.getCardInfo
import com.vm.plugin.minecraft.ChatFormatter
import com.vm.plugin.minecraft.Sender.send
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
        if (e.hand != EquipmentSlot.HAND) {
            return
        }

        val p = e.player
        val item = e.item ?: return
        val cardData = item.getCardInfo() ?: return

        val cash = cardData.cash.toInt()
        val amount = item.amount

        p.cardToMoney(item, cardData, p.isSneaking).errMsg?.let {
            p send it
            return
        }

        p send ChatFormatter.cardToMoney(cash, amount)
    }
}