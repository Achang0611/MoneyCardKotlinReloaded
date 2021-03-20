package com.vm.plugin.view.commands

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.view.send
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

        p send "Testing"

        return true
    }
}