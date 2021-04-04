package com.vm.plugin.logic

import com.vm.plugin.utils.JsonManager
import com.vm.plugin.utils.StringFormatter

object ItemSettingFormatter {

    private val setting = JsonManager.cardItemSetting.fromJson(JsonManager.getTypeToken<CardFactory.CardItemSetting>())

    fun formattedSetting(cash: Double): CardFactory.CardItemSetting {
        val name = StringFormatter.format(setting.name, "{cash}" to cash)
        val lore = setting.lore.map { StringFormatter.format(it, "{cash}" to cash) }
        return CardFactory.CardItemSetting(setting.material, name, lore)
    }
}