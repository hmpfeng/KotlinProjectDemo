package com.wsst.lxns.base.http

/**
 * Created by 谢岳峰 on 2018/10/31.
 */
abstract class RefreshPageRequest() : BasicsRequest() {
    var pageSize = 1
    var pageRows = 10
}