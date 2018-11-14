package com.wsst.lxns.base

import android.os.Bundle
import com.tmall.wireless.tangram.TangramBuilder
import com.wsst.lxns.app.widget.RefreshRecyclerView
import com.wsst.lxns.base.http.BasicsResponse
import com.wsst.lxns.base.http.RefreshPageRequest
import com.wsst.lxns.base.http.RequestType
import com.wsst.lxns.base.http.RequestTypeDef
import com.wsst.lxns.config.*
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy


/**
 * Created by 谢岳峰 on 2018/10/31.
 */
abstract class RefreshPageActivity<T, R : RefreshPageRequest> : BasicsActivity() {
    private val mDataList: MutableList<T> = arrayListOf()


    @RequestTypeDef(RequestType.REQUEST_GET, RequestType.REQUEST_POST)
    @Retention(RetentionPolicy.SOURCE)
    annotation class State

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder = TangramBuilder.newInnerBuilder(activity)
    }

    override fun initData(savedInstanceState: Bundle?) {
        val mRecyclerView = getRecyclerView()
        mRecyclerView.setOnLoadingPageListener(object : RefreshRecyclerView.OnLoadingPageListener {
            override fun onRefresh(page: Int) {
                requestForPage(page)
            }

            override fun onLoadMore(page: Int) {
                requestForPage(page)
            }
        })
    }


    override fun callBack(response: BasicsResponse?, tag: Int) {
        try {
            when (tag) {
                LOAD -> adjustList(response)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (getRecyclerView().getRefreshRecyclerLayout() != null) {
//                if(getRecyclerView().getRefreshRecyclerLayout().)
            }
        }
    }

    @State
    protected fun getMethod(): RequestType {
        return RequestType.REQUEST_GET
    }

    protected abstract fun getRecyclerView(): RefreshRecyclerView

    protected abstract fun adjustList(response: BasicsResponse?): List<T>

    protected fun getDataList(): MutableList<T> {
        return mDataList
    }

    protected fun requestForPage(page: Int) {
        mPresenter?.request(getMethod(), createRequest(page), false, if (page == 1) LOAD else LOAD_MORE)
    }

    protected abstract fun createRequest(page: Int): R
}