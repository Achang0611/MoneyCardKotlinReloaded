package com.vm.plugin.logic

import com.vm.plugin.minecraft.ChatFormatter
import com.vm.plugin.utils.Error
import com.vm.plugin.utils.JsonManager

object CardInfoValidator {
    private val rules = JsonManager.CreateCardRules.rules

    fun returnErrorIfIsInvalidValue(info: Bank.MoneyInfo): Error {
        if (!isValidAmount(info.amount)) {
            return Error(ChatFormatter.invalidAmount(info))
        }

        if (!isValidCash(info.cash)) {
            return Error(ChatFormatter.invalidCash(info))
        }

        return Error.notError()
    }

    private fun isValidAmount(amount: Int): Boolean {
        val amountRule = rules.amount
        return !(amount < amountRule.minimum || amount > amountRule.maximum)
    }

    private fun isValidCash(cash: Double): Boolean {
        val cashRule = rules.cash
        return !(cash < cashRule.minimum || cash > cashRule.maximum)
    }
}