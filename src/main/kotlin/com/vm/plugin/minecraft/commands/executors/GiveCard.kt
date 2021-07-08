package com.vm.plugin.minecraft.commands.executors

import com.vm.plugin.CardLogger
import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.logic.Bank
import com.vm.plugin.logic.CardConverter.moneyToCard
import com.vm.plugin.minecraft.ChatFormatter
import com.vm.plugin.minecraft.Permissions
import com.vm.plugin.minecraft.RequirePermissible
import com.vm.plugin.minecraft.Sender.hasPermission
import com.vm.plugin.minecraft.Sender.send
import com.vm.plugin.minecraft.commands.ArgExecutor
import com.vm.plugin.minecraft.commands.Helper
import com.vm.plugin.minecraft.commands.PlayerArgExecutor
import com.vm.plugin.utils.Error.Companion.throwIfNotNull
import com.vm.plugin.utils.JsonManager
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GiveCard : PlayerArgExecutor(), Helper, RequirePermissible {

    override val required = Permissions.CmdGive

    override var nextExecutor: LinkedHashMap<String, ArgExecutor> = LinkedHashMap()
    private val message = JsonManager.Message

    override fun execute(sender: CommandSender, args: List<String>) {
        // card give <target> <cash> [<amount = 1>]
        sender as Player

        if (!sender.hasPermission(required)) {
            CardLogger.refuseCommand(sender, required)
            sender send ChatFormatter.notPermission(required)
            return
        }

        val target = args.getOrNull(0)?.let {
            MoneyCardKotlin.instance.server.getPlayer(it) ?: run {
                sender send ChatFormatter.playerNotFound(it)
                return
            }
        } ?: run {
            sendHelp(sender)
            return
        }

        val cash = args.getOrNull(1)?.toDoubleOrNull() ?: return sendHelp(sender)
        val amount = args.getOrNull(2)?.toIntOrNull() ?: 1

        val info = Bank.MoneyInfo(cash, amount)

        target.moneyToCard(info).errMsg?.let {
            sender send it
            return
        }

        CardLogger.cardEvent(sender, target, info)

        sender.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
        sender send ChatFormatter.moneyToCardToPlayer(info, target.name)
        target.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
        target send ChatFormatter.moneyToCardFromPlayer(info, sender.name)
    }

    override fun sendHelp(sender: CommandSender) {
        val (msg, err) = message.getValue("help.Give")
        err.throwIfNotNull()
        sender send msg
    }
}