package com.vm.plugin

import com.vm.plugin.view.commands.MoneyCardCommand
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

data class A(val name: String, val age: Short)

class MoneyCardKotlin : JavaPlugin() {

    companion object {
        lateinit var instance: MoneyCardKotlin
            private set
    }

    init {
        instance = this
    }

    private val log: Logger = Logger.getLogger("Minecraft")
    private lateinit var econ: Economy

    override fun onEnable() {
        econ = setupEconomy() ?: run {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", description.name))
            server.pluginManager.disablePlugin(this)
            return
        }

        //commands
        MoneyCardCommand()
    }

    private fun setupEconomy(): Economy? {
        server.pluginManager.getPlugin("Vault") ?: return null

        val rsp = server.servicesManager.getRegistration(
            Economy::class.java
        ) ?: return null

        return rsp.provider
    }
}