package com.vm.plugin.minecraft

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

object Sender {
    infix fun CommandSender.send(message: String) {
        this.sendMessage(ChatColor.translateAlternateColorCodes('&', message))
    }

    infix fun CommandSender.send(messages: Array<out String>) {
        for (i in messages) {
            this send i
        }
    }
}