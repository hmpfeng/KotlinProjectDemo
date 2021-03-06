package com.wsst.lxns.base

import android.os.Bundle
import com.wsst.lxns.app.widget.RefreshRecyclerView
import com.wsst.lxns.base.http.BasicsResponse
import com.wsst.lxns.base.http.RefreshPageRequest
import com.wsst.lxns.base.http.RequestType
import com.wsst.lxns.base.http.RequestTypeDef
import com.wsst.lxns.config.LOAD
import com.wsst.lxns.config.LOAD_MORE
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

    private fun adjustResponse(response: BasicsResponse?, tag: Int) {
        var list = adjustList(response)
        if (list.isEmpty())
            list = emptyList()
        when (tag) {
            LOAD -> {
                getDataList().clear()
                getDataList().addAll(list)
                if (list.isNotEmpty()) getRecyclerView().getRefreshRecyclerLayout()?.setNoMoreData(false)
            }
            LOAD_MORE -> if (list.isNotEmpty()) {
                getDataList().addAll(list)
            } else {
                getRecyclerView().getRefreshRecyclerLayout()?.finishLoadMoreWithNoMoreData()
            }
        }
    }

    override fun callBack(r: BasicsResponse?, tag: Int) {
        try {
            adjustResponse(r, tag)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (getRecyclerView().getRefreshRecyclerLayout() != null) {

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