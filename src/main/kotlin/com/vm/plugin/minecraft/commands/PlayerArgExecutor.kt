package com.vm.plugin.minecraft.commands

import org.bukkit.entity.Player

abstract class PlayerArgExecutor : ArgExecutor<Player> {
    override fun getExecutorOrNull(executorName: String): ArgExecutor<Player>? {
        return if (nextExecutor.containsKey(executorName)) {
            nextExecutor[executorName]
        } else null
    }
}