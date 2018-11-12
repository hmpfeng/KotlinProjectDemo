package com.wsst.lxns.di.component

import com.jess.arms.di.component.AppComponent
import com.wsst.lxns.base.BasicsActivity
import com.wsst.lxns.base.BasicsFragment
import com.wsst.lxns.di.BasicsScope
import com.wsst.lxns.di.module.BasicsModule
import dagger.Component

/**
 * Created by 谢岳峰 on 2018/10/22.
 */
@BasicsScope
@Component(modules = [(BasicsModule::class)], dependencies = [(AppComponent::class)])
interface BasicsComponent {
    fun inject(activity: BasicsActivity)
    fun inject(fragment: BasicsFragment)
}