package com.wsst.lxns.app.widget

import android.content.Context
import android.util.AttributeSet
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * Created by 谢岳峰 on 2018/10/31.
 */
class RefreshRecyclerLayout : SmartRefreshLayout {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        for (index in 0 until childCount) {
            if (getChildAt(index) is RefreshRecyclerView) {
                val recyclerView = getChildAt(index) as RefreshRecyclerView
                recyclerView.bindRefresh(this)
            }
        }
    }
}