package com.wsst.lxns

import com.wsst.lxns.base.http.RefreshPageRequest
import com.wsst.lxns.config.TEST_URL

/**
 * Created by 谢岳峰 on 2019/1/3.
 */
class CTGRequest : RefreshPageRequest() {
    override val requestUrl: String
        get() = TEST_URL

    val key = "1c9b0fcb84b74"
    val cid = "0010001007"
}