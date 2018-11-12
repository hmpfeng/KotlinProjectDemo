package com.wsst.lxns.app

import android.content.Context
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
import timber.log.Timber
import com.jess.arms.utils.ArmsUtils
import com.google.gson.JsonIOException
import org.json.JSONException
import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException


/**
 * Created by 谢岳峰 on 2018/10/19.
 */
class ResponseErrorListenerImpl : ResponseErrorListener {
    override fun handleResponseError(context: Context?, t: Throwable?) {
        Timber.tag("Catch-Error").w(t!!.message)
        //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
        //这里只是对几个常用错误进行简单的处理, 展示这个类的用法, 在实际开发中请您自行对更多错误进行更严谨的处理
        var msg = "未知错误"
        if (t is UnknownHostException) {
            msg = "网络不可用"
        } else if (t is SocketTimeoutException) {
            msg = "请求网络超时"
        } else if (t is HttpException) {
            val httpException = t
            msg = convertStatusCode(httpException)
        } else if (t is JsonParseException || t is ParseException || t is JSONException || t is JsonIOException) {
            msg = "数据解析错误"
        }
        ArmsUtils.snackbarText(msg)
    }

    private fun convertStatusCode(httpException: HttpException): String {
        val msg: String
        if (httpException.code() == 500) {
            msg = "服务器发生错误"
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在"
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝"
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面"
        } else {
            msg = httpException.message()
        }
        return msg
    }
}