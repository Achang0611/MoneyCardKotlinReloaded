package com.vm.plugin.logic

import com.vm.plugin.CardLogger
import com.vm.plugin.logic.Bank.deposit
import com.vm.plugin.logic.Bank.withdraw
import com.vm.plugin.logic.CardDetector.getCardInfo
import com.vm.plugin.logic.CardFactory.getItem
import com.vm.plugin.minecraft.ChatFormatter
import com.vm.plugin.minecraft.InventoryEditor.addItemStackSafely
import com.vm.plugin.utils.Error
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object CardConverter {

    fun Player.moneyToCard(info: Bank.MoneyInfo): Error {
        return if (this.withdraw(info.getTotal())) {
            if (this addItemStackSafely MoneyCardData(info.cash).getItem(info.amount)) {
                CardInfoValidator.returnErrorIfIsInvalidValue(info)
            } else {
                if (this.deposit(info.getTotal())) {
                    Error(ChatFormatter.inventoryFull(this.name))
                } else {
                    CardLogger.cardError(this, info.hashCode())
                    Error("錯誤: 無法將錢轉移，金額: ${info.getTotal()}，錯誤代碼: ${info.hashCode()}")
                }
            }
        } else {
            Error(ChatFormatter.invalidMoney(info))
        }
    }

    private fun Player.cardToMoney(
        cardItem: ItemStack,
        cardData: MoneyCardData,
        changeAll: Boolean
    ): Pair<Bank.MoneyInfo, Error> {
        val amount = if (changeAll) cardItem.amount else 1
        val cash = cardData.cash

        return if (this.deposit(cash * amount)) {
            cardItem.amount -= amount
            Bank.MoneyInfo(cash, amount) to Error.notError()
        } else Bank.MoneyInfo.getEmptyInfo() to Error("錯誤: 無法轉帳")
    }

    fun Player.cardToMoney(cardItem: ItemStack, changeAll: Boolean): Pair<Bank.MoneyInfo, Error> {
        return this.cardToMoney(cardItem, cardItem.getCardInfo()!!, changeAll)
    }
}