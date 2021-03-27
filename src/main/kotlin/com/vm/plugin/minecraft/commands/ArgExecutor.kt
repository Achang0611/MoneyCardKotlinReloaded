package com.vm.plugin.minecraft.commands

interface ArgExecutor<T> {

    var nextExecutor: LinkedHashMap<String, ArgExecutor<T>>

    fun getExecutorOrNull(executorName: String): ArgExecutor<T>?

    fun execute(sender: T, args: List<String>)
}
