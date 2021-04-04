package com.vm.plugin.logic

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
        val cash = info.cash
        val itemAmount = info.amount

        return if (this.withdraw(cash * itemAmount)) {
            if (this addItemStackSafely MoneyCardData(cash).getItem(itemAmount)) {
                Error.notError()
            } else {
                if (this.deposit(cash * itemAmount)) {
                    Error(ChatFormatter.inventoryFull(this.name))
                } else {
                    Error("[SEVERE] 背包滿了並且無法退回金錢，總金額: ${cash * itemAmount}，請截圖給管理員處理。")
                }
            }
        } else {
            Error(ChatFormatter.invalidMoney(Bank.MoneyInfo(cash, itemAmount)))
        }
    }

    fun Player.cardToMoney(
        cardItem: ItemStack,
        info: MoneyCardData,
        changeAll: Boolean
    ): Pair<Bank.MoneyInfo, Error> {
        val amount = if (changeAll) cardItem.amount else 1
        val cash = info.cash

        return if (this.deposit(cash * amount)) {
            cardItem.amount -= amount
            Bank.MoneyInfo(cash, amount) to Error.notError()
        } else Bank.MoneyInfo.getEmptyInfo() to Error("[SEVERE] 無法轉帳")
    }

    fun Player.cardToMoney(cardItem: ItemStack, changeAll: Boolean): Pair<Bank.MoneyInfo, Error> {
        return this.cardToMoney(cardItem, cardItem.getCardInfo()!!, changeAll)
    }
}