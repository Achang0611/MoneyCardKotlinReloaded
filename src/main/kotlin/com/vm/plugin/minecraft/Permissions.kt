package com.vm.plugin.minecraft

enum class Permissions(private val permission: String) {
    Prefix("moneycard.kt"),

    //command
    Command("$Prefix.cmd"),
    CmdGet("$Command.get"),
    CmdGive("$Command.give"),
    CmdReload("$Command.reload"),

    //action
    Action("$Prefix.action"),
    ActCTM("$Action.ctm"),
    ActCBypass("$Action.cbypass");


    override fun toString(): String {
        return permission
    }
}
