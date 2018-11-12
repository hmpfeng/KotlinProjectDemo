package com.wsst.lxns.base.http

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.util.*

/**
 * Created by 谢岳峰 on 2018/8/28.
 */
abstract class BasicsRequest : Serializable, Comparable<BasicsRequest> {
    abstract val requestUrl: String

    protected var mContinueFields: MutableSet<String> = LinkedHashSet()

    //应用场景：请求成功就展示，失败就不需要展示，

    var isShowMsg = booleanArrayOf(false, true)//第一个参数：请求成功，展示json中的msg；第二个参数：请求失败，展示json中的msg

    /**
     * 决定当前参数使用Map集合还是Json串
     *
     * @return true = Json; false = Map
     */
   open val isParam: Boolean
        get() = false


    val mapParams: Map<String, Any>
        get() {
            val params = HashMap<String, Any>()
            val filedName = filedName
            for (key in filedName)
                params[key] = getFieldValueByName(key) as Map<String, Any>
            return params
        }

    val jsonParam: String
        get() {
            val jsonObject = JSONObject()
            val filedName = filedName
            for (key in filedName) {
                try {
                    jsonObject.put(key, getFieldValueByName(key))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
            return jsonObject.toString()
        }

    open val tag: Any?
        get() = null

    /**
     * 获取属性名数组
     */
    private val filedName: List<String>
        get() {
            mContinueFields.add("showMsg")
            val fields = this.javaClass.declaredFields
            val strings = ArrayList<String>()
            fieldFor@ for (field in fields) {
                for (name in mContinueFields) {
                    if (field.name == name) {
                        continue@fieldFor
                    }
                }
                strings.add(field.name)
            }
            return strings
        }

    override fun compareTo(o: BasicsRequest): Int {
        return 0
    }

    /* 根据属性名获取属性值
     * */
    private fun getFieldValueByName(fieldName: String): Any? {
        try {
            val firstLetter = fieldName.substring(0, 1).toUpperCase()
            val getter = "get" + firstLetter + fieldName.substring(1)
            val method = this.javaClass.getMethod(getter)
            return method.invoke(this)
        } catch (e: Exception) {
            return null
        }

    }
}
