package com.wsst.lxns.app.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener

/**
 * Created by 谢岳峰 on 2018/10/31.
 */
class RefreshRecyclerView : RecyclerView {


    val MODE_AUTO = 0x0001//自动加载
    val MODE_PULL_UP = 0x0002//手动加载

    private var offsetItemCount = 0//需要剔除的item数量；默认为0
    private var rowCount = 10//一页的数量，默认为10
    private var oldItem = 0

    private var mOnLoadingPageListener: OnLoadingPageListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    private var mRefreshLayout: RefreshRecyclerLayout? = null

    fun bindRefresh(mRefreshLayout: RefreshRecyclerLayout) {
        this.mRefreshLayout = mRefreshLayout
    }

    fun getRefreshRecyclerLayout(): RefreshRecyclerLayout? {
        return mRefreshLayout
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        offsetItemCount = 0
        mRefreshLayout?.setOnRefreshListener {
            it.setNoMoreData(false)
            mOnLoadingPageListener?.onRefresh(1)
        }
        mRefreshLayout?.setOnLoadMoreListener(createOnLoadMoreListener())
        offsetItemCount++//默认给显示更多view +1
        super.setAdapter(adapter)
    }

    private fun getPage(itemCount: Int, rowCount: Int): Int {
        val page: Int
        val temp = itemCount.toDouble() / rowCount
        page = if (temp % 1 > 0) {
            (temp + 2).toInt()
        } else {
            (temp + 1).toInt()
        }
        return page
    }

    /**
     * @param rowCount        一页的数量，默认为10
     * @param offsetItemCount 需要剔除的item数量；默认为0
     */
    fun setOffset(rowCount: Int, offsetItemCount: Int) {
        this.rowCount = rowCount
        this.offsetItemCount += offsetItemCount
    }

    private fun createOnLoadMoreListener(): OnLoadMoreListener {
        return OnLoadMoreListener {
            if (adapter == null)
                return@OnLoadMoreListener
            if (adapter.itemCount <= 0) {
                it.finishLoadMore(false)
                return@OnLoadMoreListener
            } else if ((adapter.itemCount - offsetItemCount) <= rowCount) {
                oldItem = 0
                it.finishLoadMore()
            }
            val newItem = adapter.itemCount - offsetItemCount - oldItem

            val page = getPage(adapter.itemCount - offsetItemCount, rowCount)
            if (newItem > 0) {
                if (newItem < rowCount) {
                    it.finishLoadMoreWithNoMoreData()
                    Logger.w("没有下一页")
                } else {
                    if (newItem % rowCount > 0) {
                        it.finishLoadMoreWithNoMoreData()
                        Logger.w("没有下一页")
                    } else if (mOnLoadingPageListener != null) {
                        mOnLoadingPageListener!!.onLoadMore(page)
                        Logger.w("有下一页:$page")
                    }
                }
                oldItem = adapter.itemCount - offsetItemCount
            } else {
                if (mOnLoadingPageListener != null) {
                    mOnLoadingPageListener!!.onLoadMore(page)
                    Logger.w("第一页:$page")
                }
            }
        }
    }

    fun setOnLoadingPageListener(mOnLoadingPageListener: OnLoadingPageListener) {
        this.mOnLoadingPageListener = mOnLoadingPageListener
    }

    interface OnLoadingPageListener {
        fun onRefresh(page: Int)

        fun onLoadMore(page: Int)
    }
}