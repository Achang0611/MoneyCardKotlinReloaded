package com.vm.plugin

import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger


class MoneyCardKotlin : JavaPlugin() {

    companion object {
        lateinit var instance: MoneyCardKotlin
    }

    private val log: Logger = Logger.getLogger("Minecraft")
    private var econ: Economy? = null

    override fun onEnable() {
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", description.name))
            server.pluginManager.disablePlugin(this)
            return
        }
        instance = this

    }

    override fun onDisable() {

    }

    private fun setupEconomy(): Boolean {
        server.pluginManager.getPlugin("Vault") ?: return false

        val rsp = server.servicesManager.getRegistration(
            Economy::class.java
        ) ?: return false

        econ = rsp.provider

        return econ != null
    }
}