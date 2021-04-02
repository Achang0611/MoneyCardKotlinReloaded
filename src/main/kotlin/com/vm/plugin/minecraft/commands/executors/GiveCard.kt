package com.vm.plugin.minecraft.commands.executors

import com.vm.plugin.MoneyCardKotlin
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

class GiveCard : PlayerArgExecutor(), Helper {

    override var nextExecutor: LinkedHashMap<String, ArgExecutor<Player>> = LinkedHashMap()
    private val message = JsonManager.Message

    override fun execute(sender: Player, args: List<String>) {
        // card give <target> <cash> [<amount = 1>]

        val target = args.getOrNull(0)?.let {
            MoneyCardKotlin.instance.server.getPlayer(it) ?: run {
                sender send ChatFormatter.playerNotFound(it)
                return
            }
        } ?: run {
            sendHelp(sender)
            return
        }

        val cash = args.getOrNull(1)?.toIntOrNull() ?: return sendHelp(sender)
        val amount = args.getOrNull(2)?.toIntOrNull() ?: 1

        target.moneyToCard(cash, amount).errMsg?.let {
            sender send it
            return
        }

        val (msg, err) = message.getValue("general.MoneyToCardToPlayer")
        err.throwIfNotNull()
        sender send msg

        val (msg2, err2) = message.getValue("general.MoneyToCardFromPlayer")
        err2.throwIfNotNull()
        target send msg2

    }

    override fun sendHelp(sender: CommandSender) {
        val (msg, err) = message.getValue("help.Give")
        err.throwIfNotNull()
        sender send msg
    }
}