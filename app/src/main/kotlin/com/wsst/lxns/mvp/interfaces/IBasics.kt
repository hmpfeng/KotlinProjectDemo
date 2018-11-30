package com.wsst.lxns.mvp.interfaces

import android.app.Activity
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.wsst.lxns.base.http.BasicsRequest
import com.wsst.lxns.base.http.BasicsResponse
import com.wsst.lxns.base.http.WSSTResponse
import io.reactivex.Observable

/**
 * Created by 谢岳峰 on 2018/10/18.
 */
interface IBasics {
    interface View : IView {
        fun  callBack(r: BasicsResponse?, tag: Int)
        val activity: Activity
    }

    interface Model : IModel {
        operator fun <T : BasicsRequest> get(t: T): Observable<WSSTResponse>

        fun <T : BasicsRequest> post(t: T): Observable<WSSTResponse>
    }
}