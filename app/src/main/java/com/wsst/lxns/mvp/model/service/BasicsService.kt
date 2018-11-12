package com.wsst.lxns.mvp.model.service

import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 * Created by 谢岳峰 on 2018/8/28.
 */
interface BasicsService {
    @FormUrlEncoded
    @POST
    fun postForm(@Url url: String, @FieldMap map: Map<String, Any>): Observable<ResponseBody>

    @POST
    fun postBody(@Url url: String, @Body requestBody: RequestBody): Observable<ResponseBody>

    @GET
    operator fun get(@Url url: String, @QueryMap map: Map<String, Any>): Observable<ResponseBody>

    @GET("{url}?{json}")
    operator fun get(@Path("url") url: String, @Path("json") json: String): Observable<ResponseBody>
}
