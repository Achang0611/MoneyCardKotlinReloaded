package com.vm.plugin.minecraft.commands

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.logic.CardConverter.moneyToCard
import com.vm.plugin.minecraft.Sender.send
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MoneyCardCommand : CommandExecutor, PlayerArgExecutor(), Helper {

    init {
        MoneyCardKotlin.instance.getCommand("moneycard")?.setExecutor(this)
            ?: throw InternalError("Cannot register command")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val p = sender as? Player ?: run {
            TODO("send \"不支援的使用者\"")
        }

        execute(p, args)

        return true
    }

    override fun execute(sender: Player, args: Array<out String>) {
        val cash = args.getOrNull(0)?.toIntOrNull() ?: return sender.sendHelp()
        val amount = args.getOrNull(1)?.toIntOrNull() ?: return sender.sendHelp()
        sender.moneyToCard(cash, amount).message?.let {
            sender send it
        }
    }

    override fun CommandSender.sendHelp() {
        TODO("Not yet implemented")
    }
}