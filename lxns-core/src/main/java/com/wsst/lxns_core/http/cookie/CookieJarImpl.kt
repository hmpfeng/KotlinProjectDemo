package com.wsst.lxns_core.http.cookie

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Created by admin on 2018/3/13.
 */

class CookieJarImpl(val cookieStore: CookieStore?) : CookieJar {

    init {
        if (cookieStore == null) throw IllegalArgumentException("cookieStore can not be null.")
    }

    @Synchronized
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore!!.add(url, cookies)
    }

    @Synchronized
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore!!.get(url)
    }
}
