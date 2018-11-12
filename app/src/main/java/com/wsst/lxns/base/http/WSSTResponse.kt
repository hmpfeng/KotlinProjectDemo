package com.wsst.lxns.base.http

import okhttp3.ResponseBody

/**
 * Created by 谢岳峰 on 2018/9/7.
 */
class WSSTResponse {
    var responseBody: ResponseBody
    var isShowToast = booleanArrayOf(false, true)

    constructor(responseBody: ResponseBody) {
        this.responseBody = responseBody
    }

    constructor(responseBody: ResponseBody, isShowToast: BooleanArray) {
        this.responseBody = responseBody
        this.isShowToast = isShowToast
    }
}
