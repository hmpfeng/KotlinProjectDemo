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

    /**
     * 绑定刷新框架
     * @param mRefreshLayout SmartRefreshLayout
     */
    fun bindRefresh(mRefreshLayout: RefreshRecyclerLayout) {
        this.mRefreshLayout = mRefreshLayout
    }

    /**
     * 获取刷新框架
     * @return SmartRefreshLayout
     */
    fun getRefreshRecyclerLayout(): RefreshRecyclerLayout? {
        return mRefreshLayout
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        offsetItemCount = 0
        mRefreshLayout?.setOnRefreshListener {
            it.setNoMoreData(false)//取消没有更多数据提醒
            mOnLoadingPageListener?.onRefresh(1) //开始刷新
        }
        mRefreshLayout?.setOnLoadMoreListener(createOnLoadMoreListener())//加载更多数据
        offsetItemCount++//默认给显示更多view +1
        super.setAdapter(adapter)
    }

    /**
     * 获取当前页数
     * @param itemCount 当前Item剔除过后的数量
     * @param rowCount  一页的数量，默认为10
     * @return 当前页数
     */
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
                //加载失败
                it.finishLoadMore(false)
                return@OnLoadMoreListener
            } else if ((adapter.itemCount - offsetItemCount) <= rowCount) {
                //加载成功
                oldItem = 0
                it.finishLoadMore()
            }
            val newItem = adapter.itemCount - offsetItemCount - oldItem //加载成功的 新条目数
            //获取当前页数
            val page = getPage(adapter.itemCount - offsetItemCount, rowCount)
            if (newItem > 0) {
                if (newItem < rowCount) {
                    //设置为没有更多数据，如果需要再一次加载数据调用setNoMoreData(false)打开
                    it.finishLoadMoreWithNoMoreData()
                    Logger.w("没有下一页")
                } else {
                    if (newItem % rowCount > 0) {
                        it.finishLoadMoreWithNoMoreData()
                        Logger.w("没有下一页")
                    } else if (mOnLoadingPageListener != null) {
                        mOnLoadingPageListener!!.onLoadMore(page)//加载数据
                        Logger.w("有下一页:$page")
                    }
                }
                oldItem = adapter.itemCount - offsetItemCount //记录这一次加载完成之后的Item数量
            } else {
                mOnLoadingPageListener?.onLoadMore(page)//加载数据
                Logger.w("第一页:$page")
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