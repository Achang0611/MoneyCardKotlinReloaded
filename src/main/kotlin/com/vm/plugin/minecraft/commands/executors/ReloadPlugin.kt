package com.vm.plugin.minecraft.commands.executors

import com.vm.plugin.MoneyCardKotlin
import com.vm.plugin.minecraft.Permissions
import com.vm.plugin.minecraft.RequirePermissible
import com.vm.plugin.minecraft.Sender.send
import com.vm.plugin.minecraft.commands.AnyArgExecutor
import com.vm.plugin.minecraft.commands.ArgExecutor
import com.vm.plugin.minecraft.commands.Helper
import com.vm.plugin.utils.Error.Companion.throwIfNotNull
import com.vm.plugin.utils.JsonManager
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ReloadPlugin : AnyArgExecutor(), RequirePermissible, Helper {
    override val nextExecutor: LinkedHashMap<String, ArgExecutor> = LinkedHashMap()

    override val required = Permissions.CmdReload
    private val message = JsonManager.Message

    private val plugin = MoneyCardKotlin.instance

    override fun execute(sender: CommandSender, args: List<String>) {
        plugin.server.pluginManager.disablePlugin(plugin)
        plugin.server.pluginManager.enablePlugin(plugin)

        if (sender is Player) {
            sender.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
        }
        val (msg, err) = message.getValue("general.Reloaded")
        err.throwIfNotNull()
        sender send msg
    }

    override fun sendHelp(sender: CommandSender) {
        val (msg, err) = message.getValue("help.Reload")
        err.throwIfNotNull()
        sender send msg
    }
}