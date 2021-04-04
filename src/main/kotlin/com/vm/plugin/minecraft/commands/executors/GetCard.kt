package com.vm.plugin.minecraft.commands.executors

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

class GetCard : PlayerArgExecutor(), Helper, RequirePermissible {

    override val required = Permissions.CmdGet

    override var nextExecutor: LinkedHashMap<String, ArgExecutor> = LinkedHashMap()
    private val message = JsonManager.Message

    override fun execute(sender: CommandSender, args: List<String>) {
        // card get <cash> [<amount = 1>]
        sender as Player

        if (!sender.hasPermission(required)) {
            sender send ChatFormatter.notPermission(required)
            return
        }

        val cash = args.getOrNull(0)?.toDoubleOrNull() ?: return sendHelp(sender)
        val amount = args.getOrNull(1)?.toIntOrNull() ?: 1

        val info = Bank.MoneyInfo(cash, amount)

        sender.moneyToCard(info).errMsg?.let {
            sender send it
            return
        }

        sender.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
        sender send ChatFormatter.moneyToCard(info)
    }

    override fun sendHelp(sender: CommandSender) {
        val (msg, err) = message.getValue("help.Get")
        err.throwIfNotNull()
        sender send msg
    }
}