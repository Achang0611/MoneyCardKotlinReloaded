package com.vm.plugin

import com.vm.plugin.logic.Bank
import com.vm.plugin.minecraft.commands.MoneyCardCommand
import com.vm.plugin.minecraft.events.CardChangeToMoneyEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class MoneyCardKotlin : JavaPlugin() {

    companion object {
        lateinit var instance: MoneyCardKotlin
            private set
    }

    init {
        instance = this
    }

    private val log: Logger = Logger.getLogger("Minecraft")

    override fun onEnable() {
        if (!Bank.setupEconomy()) {
            log.severe("${description.name} - Disabled due to no Vault dependency found!")
            server.pluginManager.disablePlugin(this)
            return
        }

        //commands
        MoneyCardCommand()

        //events
        CardChangeToMoneyEvent()
    }
}