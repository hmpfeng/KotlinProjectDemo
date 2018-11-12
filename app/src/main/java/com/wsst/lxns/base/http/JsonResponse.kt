package com.wsst.lxns.base.http

import android.os.Message
import android.text.TextUtils

import com.google.gson.Gson
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.ArmsUtils
import com.wsst.lxns.app.GlobalConfig

import org.json.JSONObject

/**
 * Created by 谢岳峰 on 2018/9/7.
 */
class JsonResponse : BasicsResponse() {

    @Throws(IllegalArgumentException::class)
    override fun <T> getBean(clazz: Class<T>, isFull: Boolean): T {
        if (!isFull && TextUtils.isEmpty(data)) {
            throw IllegalArgumentException("In the JsonResponse, data can't be empty")
        } else if (isFull && TextUtils.isEmpty(fullData)) {
            throw IllegalArgumentException("In the JsonResponse, Ful data can't be empty")
        }
        var objectClass: T? = null
        if (isFull && TextUtils.isEmpty(data)) {
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
            objectClass = gson.fromJson(if (isFull) fullData else data, clazz)
        }
        return objectClass!!
    }

    companion object {

        fun getResponse(json: String, isShowToast: BooleanArray?): JsonResponse {
            val mResponse = JsonResponse()
            mResponse.fullData = json
            var return_code = 1
            var data_toal = 0
            var error_code = 1
            var msg: String? = ""
            var data = ""
            val jsonObject: JSONObject?
            try {
                jsonObject = JSONObject(json)
                val hasCode = jsonObject.has("error_Code")
                if (hasCode)
                    error_code = jsonObject.getInt("error_Code")
                val hasMsg = jsonObject.has("error_Msg")
                if (hasMsg)
                    msg = jsonObject.getString("error_Msg")
                val hasRCode = jsonObject.has("return_code")
                if (hasRCode)
                    return_code = jsonObject.getInt("return_code")
                val hasToal = jsonObject.has("data_total")
                if (hasToal)
                    data_toal = jsonObject.getInt("data_toal")
                val hasData = jsonObject.has("data")
                if (hasData)
                    data = jsonObject.getString("data")
                if (isShowToast != null) {
                    if (error_code != 0 && return_code != 0 && isShowToast[1]) {
                        //提示Toast
                        ArmsUtils.snackbarText(msg)
                    } else if (msg != null && msg != "" && error_code == 0 && isShowToast[0]) {
                        //提示Toast
                        ArmsUtils.snackbarText(msg)
                    }
                }

                if (error_code == 301) {//TOKEN失效，直接回滚到登录界面
                    val message = Message()
                    message.what = GlobalConfig.APP_TIMEOUT
                    AppManager.post(message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            mResponse.return_code = return_code
            mResponse.data_total = data_toal
            mResponse.error_code = error_code
            mResponse.error_msg = msg
            mResponse.data = data
            return mResponse
        }
    }
}
