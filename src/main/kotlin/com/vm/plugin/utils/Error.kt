package com.vm.plugin.utils

data class Error(val errMsg: String?) : Throwable(errMsg) {
    companion object {
        fun notError(): Error = Error(null)
        fun jsonKeyNotFound(key: String): Error = Error("找不到Json鍵 $key 請確認插件資料夾的Json檔案是否更新至最新")

        fun Error.throwIfNotNull() {
            this.errMsg?.let {
                throw this
            }
        }
    }
}