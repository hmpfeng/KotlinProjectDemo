package com.wsst.lxns.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.wsst.lxns.expand.getValue
import com.wsst.lxns.expand.putValue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
/**
* @author 苏磊
* @describe 委托属性
*/
public final class ProxyMethod<T>(private val name: String, private val default: T) : ReadWriteProperty<Any?, T> {
    companion object {
        private lateinit var sp: SharedPreferences;
        fun initPreferences(context: Context) {
            sp = context.getSharedPreferences(this.javaClass.name, 0)
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = sp.getValue(name, default)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = sp.putValue(name, value)

}