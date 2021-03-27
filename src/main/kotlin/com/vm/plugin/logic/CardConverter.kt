package com.vm.plugin.logic

import com.vm.plugin.logic.Bank.deposit
import com.vm.plugin.logic.Bank.withdraw
import com.vm.plugin.logic.CardFactory.getItem
import com.vm.plugin.minecraft.InventoryEditor.addItemStackSafely
import com.vm.plugin.utils.JsonManager
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object CardConverter {

    private val message = JsonManager.Message

    fun Player.moneyToCard(cash: Int, itemAmount: Int): Error {
        return if (this.withdraw(cash * itemAmount)) {
            if (this addItemStackSafely MoneyCardData(cash.toDouble()).getItem(itemAmount)) {
                Error.notError()
            } else {
                if (this.deposit(cash * itemAmount)) {
                    val (msg, err) = message.getValue("warning.InventoryFull")
                    if (err.errMsg != null) err else Error(msg)
                } else {
                    Error("[SEVERE] 背包滿了並且無法退回金錢，金額: ${cash * itemAmount}，請截圖給管理員處理。")
                }
            }
        } else {
            val (msg, err) = message.getValue("warning.InvalidMoney")
            if (err.errMsg != null) err else Error(msg)
        }
    }

    fun Player.cardToMoney(cardItem: ItemStack, info: MoneyCardData, changeAll: Boolean): Error {
        val amount = if (changeAll) cardItem.amount else 1

        return if (this.deposit(info.cash * amount)) {
            cardItem.amount -= amount
            Error.notError()
        } else Error("[SEVERE] 無法轉帳")
    }
}