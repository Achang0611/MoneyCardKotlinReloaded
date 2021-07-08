package com.vm.plugin.logic

import com.vm.plugin.MoneyCardKotlin
import net.milkbowl.vault.economy.Economy
import org.bukkit.OfflinePlayer
import java.util.*

object Bank {

    private lateinit var econ: Economy

    data class MoneyInfo(val cash: Double, val amount: Int) {
        companion object {
            fun getEmptyInfo(): MoneyInfo = MoneyInfo(0.0, 0)
        }

        private val uuid by lazy { UUID.randomUUID()!! }

        fun getTotal(): Double {
            return this.amount * this.cash
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is MoneyInfo) return false

            if (cash != other.cash) return false
            if (amount != other.amount) return false
            if (uuid != other.uuid) return false

            return true
        }

        override fun hashCode(): Int {
            return uuid.hashCode()
        }


    }

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