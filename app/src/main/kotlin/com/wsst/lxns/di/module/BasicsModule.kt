package com.wsst.lxns.di.module

import com.tbruyelle.rxpermissions2.RxPermissions
import com.wsst.lxns.di.BasicsScope
import com.wsst.lxns.mvp.interfaces.IBasics
import com.wsst.lxns.mvp.model.BasicsModel
import dagger.Module
import dagger.Provides

/**
 * Created by 谢岳峰 on 2018/10/22.
 */
@Module
class BasicsModule(private var view: IBasics.View?) {

    @BasicsScope
    @Provides
    fun providesView(): IBasics.View = view!!

    @BasicsScope
    @Provides
    fun providesModel(model: BasicsModel): IBasics.Model = model

    @BasicsScope
    @Provides
    fun providesRxPermissions(): RxPermissions = RxPermissions(view!!.activity)
}