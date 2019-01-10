package com.wsst.lxns.base.http

import org.json.JSONException

/**
 * Created by 谢岳峰 on 2018/10/18.
 */
abstract class BasicsResponse {
    var fullData: String? = "" //完整json
    var msg: String? = ""
    var retCode: Int? = 0
    var resultJson: String? = "" //内容json

    /**
     * 解析单条数据
     */
    @Throws(IllegalArgumentException::class, JSONException::class)
    abstract fun <T> getBean(clazz: Class<T>, isFull: Boolean): T
}