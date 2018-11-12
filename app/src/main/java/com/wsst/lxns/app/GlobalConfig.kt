package com.wsst.lxns.app

/**
 * Created by 谢岳峰 on 2018/10/18.
 */
interface GlobalConfig {
    companion object {
        const val APP_LOGOUT = 5005
        const val APP_TIMEOUT = 5006
        const val API = ""

        const val LOAD = 1 shl 1
        const val LOAD_MORE = 1 shl 2
    }
}