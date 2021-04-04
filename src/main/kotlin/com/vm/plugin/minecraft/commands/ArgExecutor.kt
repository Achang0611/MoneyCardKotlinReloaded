package com.vm.plugin.minecraft.commands

import org.bukkit.command.CommandSender

abstract class ArgExecutor {

    abstract val requiredPlayer: Boolean

    abstract val nextExecutor: LinkedHashMap<String, ArgExecutor>

    fun getExecutorOrNull(executorName: String, requiredPlayer: Boolean = false): ArgExecutor? {
        val default = nextExecutor.getOrDefault(executorName, null)
        if (requiredPlayer && default != null) {
            return if (default.requiredPlayer) default else null
        }
        return default
    }

    abstract fun execute(sender: CommandSender, args: List<String>)
}
