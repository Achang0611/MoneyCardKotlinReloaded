package com.vm.plugin.minecraft

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

    private fun aboutMoney(path: String, cash: Int, amount: Int, vararg extension: Pair<String, Any>): String {
        return baseFormat(
            path,
            "{cash}" to cash,
            "{amount}" to amount,
            "{total_cash}" to cash * amount,
            *extension
        )
    }

    fun moneyToCard(cash: Int, amount: Int): String {
        return aboutMoney("general.MoneyToCard", cash, amount)
    }

    fun moneyToCardToPlayer(cash: Int, amount: Int, toPlayerName: String): String {
        return aboutMoney("general.MoneyToCardToPlayer", cash, amount, "player" to toPlayerName)
    }

    fun moneyToCardFromPlayer(cash: Int, amount: Int, fromPlayerName: String): String {
        return aboutMoney("general.MoneyToCardFromPlayer", cash, amount, "player" to fromPlayerName)
    }

    fun cardToMoney(cash: Int, amount: Int): String {
        return aboutMoney("general.CardToMoney", cash, amount)
    }

    fun invalidMoney(cash: Int, amount: Int): String {
        return aboutMoney("warning.InvalidMoney", cash, amount)
    }

    fun playerNotFound(playerName: String): String {
        return baseFormat("warning.PlayerNotFound", "{player}" to playerName)
    }

    fun notPermission(permissions: Permissions): String {
        return baseFormat("warning.NotPermission", "perm" to permissions)
    }
}