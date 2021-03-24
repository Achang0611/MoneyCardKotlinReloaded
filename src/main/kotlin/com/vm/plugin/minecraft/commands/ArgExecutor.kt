package com.vm.plugin.minecraft.commands

interface ArgExecutor<T> {
    fun execute(sender: T, args: Array<out String>)
}
