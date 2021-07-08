package com.vm.plugin.minecraft

import com.vm.plugin.logic.Bank
import com.vm.plugin.utils.Error.Companion.throwIfNotNull
import com.vm.plugin.utils.JsonManager
import com.vm.plugin.utils.StringFormatter

object ChatFormatter {

    private val message = JsonManager.Message

    private fun baseFormat(path: String, vararg extension: Pair<String, Any>): String {
        val (msg, err) = message.getValue(path)
        err.throwIfNotNull()
        return StringFormatter.format(msg, *extension)
    }

    private fun aboutMoney(path: String, info: Bank.MoneyInfo, vararg extension: Pair<String, Any>): String {
        return baseFormat(
            path,
            "{cash}" to info.cash,
            "{amount}" to info.amount,
            "{total_cash}" to info.cash * info.amount,
            *extension
        )
    }

    fun moneyToCardToPlayer(info: Bank.MoneyInfo, toPlayerName: String): String {
        return aboutMoney("general.MoneyToCardToPlayer", info, "{player}" to toPlayerName)
    }

    fun moneyToCardFromPlayer(info: Bank.MoneyInfo, fromPlayerName: String): String {
        return aboutMoney("general.MoneyToCardFromPlayer", info, "{player}" to fromPlayerName)
    }

    fun moneyToCard(info: Bank.MoneyInfo): String {
        return aboutMoney("general.MoneyToCard", info)
    }

    fun cardToMoney(info: Bank.MoneyInfo): String {
        return aboutMoney("general.CardToMoney", info)
    }

    fun invalidMoney(info: Bank.MoneyInfo): String {
        return aboutMoney("warning.InvalidMoney", info)
    }

    fun invalidAmount(info: Bank.MoneyInfo): String {
        val amountRule = JsonManager.CreateCardRules.rules.amount
        return aboutMoney(
            "warning.InvalidAmount", info,
            "{amount_range}" to "${amountRule.minimum} ~ ${amountRule.maximum}"
        )
    }

    fun invalidCash(info: Bank.MoneyInfo): String {
        val cashRule = JsonManager.CreateCardRules.rules.cash
        return aboutMoney(
            "warning.InvalidCash", info,
            "{cash_range}" to "${cashRule.minimum} ~ ${cashRule.maximum}"
        )
    }

    fun playerNotFound(playerName: String): String {
        return baseFormat("warning.PlayerNotFound", "{player}" to playerName)
    }

    fun inventoryFull(playerName: String): String {
        return baseFormat("warning.InventoryFull", "{player}" to playerName)
    }

    fun notPermission(permissions: Permissions): String {
        return baseFormat("warning.NotPermission", "{perm}" to permissions)
    }
}