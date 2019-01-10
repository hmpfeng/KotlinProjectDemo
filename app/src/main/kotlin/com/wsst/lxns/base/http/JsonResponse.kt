package com.wsst.lxns.base.http

import android.os.Message
import android.text.TextUtils

import com.google.gson.Gson
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.ArmsUtils
import com.wsst.lxns.config.APP_TIMEOUT

import org.json.JSONObject

/**
 * Created by 谢岳峰 on 2018/9/7.
 */
open class JsonResponse : BasicsResponse() {

    @Throws(IllegalArgumentException::class)
    override fun <T> getBean(clazz: Class<T>, isFull: Boolean): T {
        if (!isFull && TextUtils.isEmpty(resultJson)) {
            throw IllegalArgumentException("In the JsonResponse, data can't be empty")
        } else if (isFull && TextUtils.isEmpty(fullData)) {
            throw IllegalArgumentException("In the JsonResponse, Ful data can't be empty")
        }
        var objectClass: T? = null
        if (isFull && TextUtils.isEmpty(resultJson)) {
            try {
                return Class.forName(clazz.name).newInstance() as T
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

        } else {
            val gson = Gson()
            objectClass = gson.fromJson(if (isFull) fullData else resultJson, clazz)
        }
        return objectClass!!
    }

    companion object {

        fun getResponse(json: String, isShowToast: BooleanArray?): JsonResponse {
            val mResponse = JsonResponse()
            mResponse.fullData = json
            var retCode = 1
            var msg: String? = ""
            var resultJson = ""
            val jsonObject: JSONObject?
            try {
                jsonObject = JSONObject(json)
                val hasCode = jsonObject.has("retCode")
                if (hasCode)
                    retCode = jsonObject.getInt("retCode")
                val hasMsg = jsonObject.has("msg")
                if (hasMsg)
                    msg = jsonObject.getString("msg")
                val hasData = jsonObject.has("result")
                if (hasData)
                    resultJson = jsonObject.getString("result")
                if (isShowToast != null) {
                    if (retCode != 200 && isShowToast[1]) {
                        //提示Toast
                        ArmsUtils.snackbarText(msg)
                    } else if (msg != null && msg != "" && isShowToast[0]) {
                        //提示Toast
                        ArmsUtils.snackbarText(msg)
                    }
                }

                if (retCode == 301) {//TOKEN失效，直接回滚到登录界面
                    val message = Message()
                    message.what = APP_TIMEOUT
                    AppManager.post(message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            mResponse.retCode = retCode
            mResponse.msg = msg
            mResponse.resultJson = resultJson
            return mResponse
        }
    }
}
