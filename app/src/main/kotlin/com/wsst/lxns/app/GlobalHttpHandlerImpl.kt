package com.wsst.lxns.app

import android.content.Context
import android.text.TextUtils
import com.jess.arms.http.GlobalHttpHandler
import com.jess.arms.http.log.RequestInterceptor
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by 谢岳峰 on 2018/10/19.
 */
class GlobalHttpHandlerImpl : GlobalHttpHandler {

    private var context: Context? = null

    constructor(context: Context?) {
        this.context = context
    }


    override fun onHttpRequestBefore(chain: Interceptor.Chain?, request: Request?): Request {

        return request!!
    }

    override fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain?, response: Response?): Response {
        /* 这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
           重新请求token,并重新执行请求 */
        if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response!!.body()!!.contentType())) {
            val request: Request = chain!!.request()

        }

        /* 这里如果发现token过期,可以先请求最新的token,然后在拿新的token放入request里去重新请求
           注意在这个回调之前已经调用过proceed,所以这里必须自己去建立网络请求,如使用okhttp使用新的request去请求
           create a new request and modify it accordingly using the new token
           Request newRequest = chain.request().newBuilder().header("token", newToken)
                                .build();

           retry the request

           response.body().close();
           如果使用okhttp将新的请求,请求成功后,将返回的response  return出去即可
           如果不需要返回新的结果,则直接把response参数返回出去 */
        return response!!
    }
}