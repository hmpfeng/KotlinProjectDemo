package com.wsst.lxns.mvp.presenter

import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.wsst.lxns.base.http.*
import com.wsst.lxns.mvp.interfaces.IBasics
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import javax.inject.Inject

/**
 * Created by 谢岳峰 on 2018/10/18.
 */
class BasicsPresenter
@Inject
constructor(mModel: IBasics.Model, mRootView: IBasics.View,
            private val rxPermissions: RxPermissions, private val mRxErrorHandler: RxErrorHandler)
    : BasePresenter<IBasics.Model, IBasics.View>() {

    fun <T : BasicsRequest> request(requestType: RequestType, t: T, showLoading: Boolean, tag: Int) {
        val request: Observable<WSSTResponse> = when (requestType) {
            RequestType.REQUEST_GET -> mModel.get(t)
            RequestType.REQUEST_POST -> mModel.post(t)
        }
        request.subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(1, 2))
                .doOnSubscribe { _ -> if (showLoading) mRootView.showLoading() }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { if (showLoading) mRootView.hideLoading() }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe {
                    object : JsonHandleSubscriber(mRxErrorHandler) {
                        override fun onSuccess(response: JsonResponse) {
                            mRootView.callBack(response, tag)
                        }

                        override fun onError(t: Throwable) {
                            super.onError(t)
                            mRootView.callBack(null, tag)
                        }

                        override fun onDefinedError(code: Int) {
                            mRootView.callBack(null, tag)
                        }
                    }
                }
    }

}