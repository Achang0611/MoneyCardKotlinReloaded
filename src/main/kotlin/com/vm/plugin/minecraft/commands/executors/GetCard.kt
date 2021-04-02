package com.vm.plugin.minecraft.commands.executors

import com.vm.plugin.logic.CardConverter.moneyToCard
import com.vm.plugin.minecraft.ChatFormatter
import com.vm.plugin.minecraft.Sender.send
import com.vm.plugin.minecraft.commands.ArgExecutor
import com.vm.plugin.minecraft.commands.Helper
import com.vm.plugin.minecraft.commands.PlayerArgExecutor
import com.vm.plugin.utils.Error.Companion.throwIfNotNull
import com.vm.plugin.utils.JsonManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GetCard : PlayerArgExecutor(), Helper {

    override var nextExecutor: LinkedHashMap<String, ArgExecutor<Player>> = LinkedHashMap()
    private val message = JsonManager.Message

    override fun execute(sender: Player, args: List<String>) {
        // card get <cash> [<amount = 1>]
        val cash = args.getOrNull(0)?.toIntOrNull() ?: return sendHelp(sender)
        val amount = args.getOrNull(1)?.toIntOrNull() ?: 1

        sender.moneyToCard(cash, amount).errMsg?.let {
            sender send it
            return
        }

        sender send ChatFormatter.moneyToCard(cash, amount)
    }

    override fun sendHelp(sender: CommandSender) {
        val (msg, err) = message.getValue("help.Get")
        err.throwIfNotNull()
        sender send msg
    }
}