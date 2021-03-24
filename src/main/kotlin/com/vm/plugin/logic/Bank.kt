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

    fun OfflinePlayer.withdraw(amount: Double): Boolean {
        return this.withdrawCheck(amount) && econ.withdrawPlayer(this, amount).transactionSuccess()
    }

    fun OfflinePlayer.withdraw(amount: Int): Boolean {
        return this.withdraw(amount.toDouble())
    }

    private fun OfflinePlayer.withdrawCheck(amount: Double): Boolean = econ.getBalance(this) >= amount

    fun OfflinePlayer.deposit(amount: Double): Boolean {
        return econ.depositPlayer(this, amount).transactionSuccess()
    }

    fun OfflinePlayer.deposit(amount: Int): Boolean {
        return this.deposit(amount.toDouble())
    }
}