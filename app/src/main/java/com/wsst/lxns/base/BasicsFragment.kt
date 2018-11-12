package com.wsst.lxns.base

import android.content.Intent
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.wsst.lxns.di.component.DaggerBasicsComponent
import com.wsst.lxns.di.module.BasicsModule
import com.wsst.lxns.mvp.interfaces.IBasics
import com.wsst.lxns.mvp.presenter.BasicsPresenter

/**
 * Created by 谢岳峰 on 2018/10/22.
 */
abstract class BasicsFragment : BaseFragment<BasicsPresenter>(), IBasics.View {
    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerBasicsComponent.builder()
                .appComponent(appComponent)
                .basicsModule(BasicsModule(this))
                .build()
                .inject(this)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        checkNotNull(message)
        ArmsUtils.makeText(context, message)
    }

    override fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }
}