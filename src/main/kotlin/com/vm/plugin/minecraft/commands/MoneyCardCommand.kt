package com.vm.plugin.minecraft.commands

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.logic.CardFactory.getItem
import com.vm.plugin.logic.MoneyCardData
import com.vm.plugin.minecraft.addItemStackSafely
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MoneyCardCommand : CommandExecutor {

    init {
        MoneyCardKotlin.instance.getCommand("moneycard")?.setExecutor(this)
            ?: throw InternalError("Cannot register command")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val p = sender as? Player ?: run {
            TODO("send \"Unsupported Sender\"")
        }

        val cash = args.getOrNull(0)?.toIntOrNull() ?: 0
        val amount = args.getOrNull(1)?.toIntOrNull() ?: 1
        p addItemStackSafely MoneyCardData.create(p, p, cash).getItem(amount)


        return true
    }
}