package com.vm.plugin.logic

import com.vm.plugin.MoneyCardKotlin
import net.milkbowl.vault.economy.Economy
import org.bukkit.OfflinePlayer

object Bank {

    private lateinit var econ: Economy

    fun setupEconomy(): Boolean {
        MoneyCardKotlin.instance.run {
            server.pluginManager.getPlugin("Vault") ?: return false
            val rsp = server.servicesManager.getRegistration(Economy::class.java) ?: return false
            econ = rsp.provider
            return true
        }
    }

    fun OfflinePlayer.withdraw(amount: Double) {
        econ.withdrawPlayer(this, amount)
    }

    fun OfflinePlayer.withdraw(amount: Int) {
        econ.withdrawPlayer(this, amount.toDouble())
    }

    fun OfflinePlayer.deposit(amount: Double) {
        econ.depositPlayer(this, amount)
    }

    fun OfflinePlayer.deposit(amount: Int) {
        econ.depositPlayer(this, amount.toDouble())
    }
}