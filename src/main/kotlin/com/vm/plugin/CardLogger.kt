package com.vm.plugin

import com.vm.plugin.logic.Bank
import com.vm.plugin.minecraft.Permissions
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.Closeable
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.Logger

object CardLogger : Closeable {
    override fun close() {
        logger.handlers.forEach(Handler::close)
        logger.handlers.forEach { logger.removeHandler(it) }
    }

    fun logSender(level: Level, sender: CommandSender, message: String) {
        logger.log(level, "${sender.name} $message")
    }

    fun refuseCommand(sender: CommandSender, perm: Permissions) {
        logSender(Level.INFO, sender, "被拒絕使用指令 權限: $perm")
    }

    fun logMoneyInfo(player: Player, message: String, info: Bank.MoneyInfo) {
        logSender(
            Level.INFO,
            player,
            "$message 單個價值: ${info.cash} 數量: ${info.amount} 總價值: ${info.getTotal()} 雜湊值: ${info.hashCode()}"
        )
    }

    fun cardEvent(from: Player, to: Player, info: Bank.MoneyInfo) {
        logMoneyInfo(from, "給 ${to.name}", info)
    }

    fun moneyEvent(player: Player, info: Bank.MoneyInfo) {
        logMoneyInfo(player, "兌換卡片", info)
    }

    fun cardError(player: Player, hashCode: Int) {
        logSender(Level.SEVERE, player, "未知的錯誤，雜湊值: $hashCode")
    }

    val logger: Logger by lazy {
        Logger.getLogger(MoneyCardKotlin::class.java.simpleName)
    }
}