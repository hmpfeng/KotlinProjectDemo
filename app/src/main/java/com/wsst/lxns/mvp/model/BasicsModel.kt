package com.wsst.lxns.mvp.model

import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.wsst.lxns.base.http.BasicsRequest
import com.wsst.lxns.base.http.MediaTypes
import com.wsst.lxns.base.http.WSSTResponse
import com.wsst.lxns.mvp.interfaces.IBasics
import com.wsst.lxns.mvp.model.service.BasicsService
import io.reactivex.Observable
import io.reactivex.functions.Function
import okhttp3.RequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Created by 谢岳峰 on 2018/10/22.
 */
class BasicsModel @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager), IBasics.Model {
    private var mBasicsService: BasicsService? = null

    init {
        mBasicsService = mRepositoryManager.obtainRetrofitService(BasicsService::class.java)
    }

    override fun <T : BasicsRequest> get(t: T): Observable<WSSTResponse> {
        val responseBodyObservable: Observable<ResponseBody> = if (t.isParam)
            mBasicsService!![t.requestUrl, t.jsonParam]
        else
            mBasicsService!![t.requestUrl, t.mapParams]
        return responseBodyObservable
                .flatMap(Function<ResponseBody, Observable<WSSTResponse>> {
                    val response = WSSTResponse(it, t.isShowMsg)
                    return@Function Observable.just<WSSTResponse>(response)
                })
    }

    override fun <T : BasicsRequest> post(t: T): Observable<WSSTResponse> {
        val responseBodyObservable: Observable<ResponseBody> = if (t.isParam) {
            val requestBody: RequestBody = RequestBody.create(MediaTypes.APPLICATION_JSON_TYPE, t.jsonParam)
            mBasicsService!!.postBody(t.requestUrl, requestBody)
        } else {
            mBasicsService!!.postForm(t.requestUrl, t.mapParams)
        }
        return responseBodyObservable.flatMap(Function<ResponseBody, Observable<WSSTResponse>> {
            val response = WSSTResponse(it, t.isShowMsg)
            return@Function Observable.just<WSSTResponse>(response)
        })
    }
}