package com.vm.plugin.minecraft.commands

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.minecraft.RequirePermissible
import com.vm.plugin.minecraft.Sender.hasPermission
import com.vm.plugin.minecraft.Sender.send
import com.vm.plugin.minecraft.commands.executors.GetCard
import com.vm.plugin.minecraft.commands.executors.GiveCard
import com.vm.plugin.utils.Error.Companion.throwIfNotNull
import com.vm.plugin.utils.JsonManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MoneyCardCommand : CommandExecutor, PlayerArgExecutor(), Helper {

    override var nextExecutor: LinkedHashMap<String, ArgExecutor<Player>> = LinkedHashMap()

    init {
        MoneyCardKotlin.instance.getCommand("moneycard")?.setExecutor(this)
            ?: throw InternalError("Cannot register command")
        nextExecutor.apply {
            put("get", GetCard())
            put("give", GiveCard())
        }
    }

    private val message = JsonManager.Message

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        // card <mode>
        val p = sender as? Player ?: run {
            val (msg, err) = message.getValue("warning.UnsupportedSender")
            err.throwIfNotNull()
            sender send msg
            return true
        }

        val next = args.getOrElse(0) { "null" }
        getExecutorOrNull(next)?.execute(p, args.drop(1)) ?: sendHelp(sender)

        return true
    }

    override fun execute(sender: Player, args: List<String>) {
        throw UnsupportedOperationException()
    }

    override fun sendHelp(sender: CommandSender) {
        for (entry in nextExecutor) {
            val argExecutor = entry.value
            if (argExecutor is Helper) {
                if (argExecutor is RequirePermissible) {
                    if (!sender.hasPermission(argExecutor.required)) {
                        continue
                    }
                }

                argExecutor.sendHelp(sender)
            }
        }
    }
}