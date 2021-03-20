package com.vm.plugin.view

import org.bukkit.command.CommandSender

infix fun CommandSender.send(message: String) {
    this.sendMessage(message)
}

infix fun CommandSender.send(messages: Array<out String>) {
    this.sendMessage(messages)
}
