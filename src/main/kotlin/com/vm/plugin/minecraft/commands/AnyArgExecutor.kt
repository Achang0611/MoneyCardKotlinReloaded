package com.vm.plugin.minecraft.commands

abstract class AnyArgExecutor : ArgExecutor() {
    override val requiredPlayer = false
}