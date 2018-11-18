package com.wsst.lxns.expand

import android.content.SharedPreferences
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

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
        is Any -> deSerialize(getString(key, serialize(default)))
        else -> {
        }
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
            is Any -> putString(key, serialize(value))
            else -> throw IllegalArgumentException("This type can be get from Preferences")
        }
    }.apply()

}

private fun <T> deSerialize(str: String): T {
    val redStr = java.net.URLEncoder.encode(str, "UTF-8")
    val byteArrayInputStream = ByteArrayInputStream(
            redStr.toByteArray(charset("ISO-8859-1"))
    )
    val objectInputStream = ObjectInputStream(byteArrayInputStream)
    val obj = objectInputStream.readObject() as T
    objectInputStream.close()
    byteArrayInputStream.close()
    return obj
}

private fun <T> serialize(value: T): String {
    val byteArrayOutPutStream = ByteArrayOutputStream()
    val objectOutputStream = ObjectOutputStream(byteArrayOutPutStream)
    objectOutputStream.writeObject(value)
    var serStr = byteArrayOutPutStream.toString("ISO-8859-1")
    serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
    objectOutputStream.close()
    byteArrayOutPutStream.close()
    return serStr
}

// sp 清空
fun SharedPreferences.clear() {
    this.edit().clear().apply()
}

// sp 根据key值删除
fun SharedPreferences.clear(key: String) {
    this.edit().remove(key).apply()
}