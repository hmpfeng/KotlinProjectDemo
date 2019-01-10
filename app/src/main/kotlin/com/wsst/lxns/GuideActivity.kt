package com.wsst.lxns

import android.os.Bundle
import com.wsst.lxns.app.widget.RefreshRecyclerView
import com.wsst.lxns.base.RefreshPageActivity
import com.wsst.lxns.base.http.BasicsResponse

class GuideActivity : RefreshPageActivity<CTGResponse.ResultBean.ListBean, CTGRequest>() {
    override fun getRecyclerView(): RefreshRecyclerView = findViewById(R.id.guide_rv)

    override fun adjustList(response: BasicsResponse?): List<CTGResponse.ResultBean.ListBean> {
        val bean = response?.getBean(CTGResponse.ResultBean::class.java, false)
        return bean?.list as List<CTGResponse.ResultBean.ListBean>
    }

    override fun createRequest(page: Int): CTGRequest {
        val ctg = CTGRequest()
        ctg.page = page
        return ctg
    }

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_guide
}