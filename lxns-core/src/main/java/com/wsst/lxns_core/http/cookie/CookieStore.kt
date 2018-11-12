package com.wsst.lxns_core.http.cookie

import okhttp3.Cookie
import okhttp3.HttpUrl

/**
 * Created by admin on 2018/3/13.
 */

interface CookieStore {

    val cookies: List<Cookie>

    fun add(uri: HttpUrl, cookie: List<Cookie>)

    operator fun get(uri: HttpUrl): List<Cookie>

    fun remove(uri: HttpUrl, cookie: Cookie): Boolean

    fun removeAll(): Boolean
}
