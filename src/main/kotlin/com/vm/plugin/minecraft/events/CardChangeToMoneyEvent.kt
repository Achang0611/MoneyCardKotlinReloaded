package com.vm.plugin.minecraft.events

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.logic.CardConverter.cardToMoney
import com.vm.plugin.logic.CardDetector.isCard
import com.vm.plugin.minecraft.ChatFormatter
import com.vm.plugin.minecraft.Permissions
import com.vm.plugin.minecraft.RequirePermissible
import com.vm.plugin.minecraft.Sender.hasPermission
import com.vm.plugin.minecraft.Sender.send
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class CardChangeToMoneyEvent : Listener, RequirePermissible {

    override val required = Permissions.ActCTM

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
        if (!item.isCard()) {
            return
        }

        if (!e.player.hasPermission(required)) {
            e.player send ChatFormatter.notPermission(required)
            return
        }

        val changeAll = p.isSneaking

        val (result, err) = p.cardToMoney(item, changeAll)
        err.errMsg?.let {
            p send it
            return
        }

        p send ChatFormatter.cardToMoney(result)
    }
}
