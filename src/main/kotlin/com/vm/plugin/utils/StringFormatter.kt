package com.vm.plugin.utils

object StringFormatter {
    private fun format(str: String, from: String, to: Any): String {
        return str.replace(from, to.toString(), true)
    }

    fun format(str: String, vararg obj: Pair<String, Any>): String {
        var result = str
        obj.forEach {
            result = format(result, it.first, it.second)
        }

        return result
    }

    fun format(str: String, obj: Map<String, Any>): String {
        var result = str
        obj.forEach {
            result = format(result, it.key, it.value)
        }

        return result
    }
}