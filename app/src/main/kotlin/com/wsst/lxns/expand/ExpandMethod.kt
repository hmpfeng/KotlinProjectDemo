package com.wsst.lxns.expand

import android.content.SharedPreferences

/**
 * @author 苏磊
 * @describe 扩展方法
 */

//  sp  取值
fun <T> SharedPreferences.getValue(key: String, default: T): T = this.run {

    val any: Any = when (default) {
        is Int -> getInt(key, default)
        is String -> getString(key, default)
        is Boolean -> getBoolean(key, default)
        is Long -> getLong(key, default)
        else -> ""
    }
    any as T
}

//  sp  存值
fun <T> SharedPreferences.putValue(key: String, value: T) {
    this.edit().apply {
        when (value) {
            is Int -> putInt(key, value)
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            else -> throw IllegalArgumentException("This type can be get from Preferences")
        }
    }.apply()
}