package com.vm.plugin.minecraft.commands

import org.bukkit.command.CommandSender

interface Helper {
    fun CommandSender.sendHelp()
}