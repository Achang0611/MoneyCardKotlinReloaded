package com.vm.plugin.logic

import com.vm.plugin.logic.Bank.deposit
import com.vm.plugin.logic.Bank.withdraw
import com.vm.plugin.logic.CardFactory.getItem
import com.vm.plugin.minecraft.InventoryEditor.addItemStackSafely
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object CardConverter {
    fun Player.moneyToCard(cash: Int, itemAmount: Int): Error {
        return if (this.withdraw(cash * itemAmount)) {
            if (this addItemStackSafely MoneyCardData(cash.toDouble()).getItem(itemAmount)) {
                Error(null)
            } else {
                if (this.deposit(cash * itemAmount)) {
                    Error("背包滿了")
                } else {
                    Error("[SEVERE] 背包滿了並且無法退回金錢，金額: ${cash * itemAmount}，請截圖給管理員處理。")
                }
            }
        } else Error("錢不夠")
    }

    fun Player.cardToMoney(cardItem: ItemStack, info: MoneyCardData, changeAll: Boolean): Error {
        val amount = if (changeAll) cardItem.amount else 1
        return if (this.deposit(info.cash * amount)) {
            cardItem.amount -= amount
            Error(null)
        } else Error("無法轉帳")
    }
}