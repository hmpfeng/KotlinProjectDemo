package com.wsst.lxns.base.http

import java.io.IOException

import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber

/**
 * Created by 谢岳峰 on 2018/9/7.
 */
abstract class JsonHandleSubscriber(rxErrorHandler: RxErrorHandler) : ErrorHandleSubscriber<WSSTResponse>(rxErrorHandler) {

    override fun onNext(WSSTResponse: WSSTResponse) {
        if (WSSTResponse != null) {
            try {
                val responseBody = WSSTResponse.responseBody
                val json = responseBody.string()
                val jsonResponse = JsonResponse.getResponse(json, WSSTResponse.isShowToast)
                if (jsonResponse.error_code == 0) {
                    onSuccess(jsonResponse)
                } else {
                    onDefinedError(jsonResponse.error_code!!)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                onError(null!!)
            }

        }
    }

    abstract fun onSuccess(response: JsonResponse)

    open fun onDefinedError(code: Int) {}
}
