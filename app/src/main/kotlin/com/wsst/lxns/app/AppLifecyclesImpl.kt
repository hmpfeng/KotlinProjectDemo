package com.wsst.lxns.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.util.Log
import android.widget.ImageView
import butterknife.ButterKnife
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.integration.cache.IntelligentCache
import com.jess.arms.utils.ArmsUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.tmall.wireless.tangram.TangramBuilder
import com.tmall.wireless.tangram.util.IInnerImageSetter
import com.wsst.lxns.BuildConfig
import com.wsst.lxns.utils.Preference
import timber.log.Timber


/**
 * Created by 谢岳峰 on 2018/10/19.
 */
class AppLifecyclesImpl : AppLifecycles {
    override fun attachBaseContext(base: Context) {
        MultiDex.install(base)
    }

    override fun onCreate(application: Application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        if (BuildConfig.DEBUG) {//Timber初始化
            //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
            //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
            //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
//            Timber.plant(Timber.DebugTree())
            // 如果你想将框架切换为 Logger 来打印日志,请使用下面的代码,如想切换为其他日志框架请根据下面的方式扩展
            Logger.addLogAdapter(AndroidLogAdapter())
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    Logger.log(priority, tag, message, t)
                }
            })
            ButterKnife.setDebug(true)
        }
        //LeakCanary 内存泄露检查
        //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
        //否则存储在 LRU 算法的存储空间中, 前提是 extras 使用的是 IntelligentCache (框架默认使用)
        ArmsUtils.obtainAppComponentFromContext(application).extras()
                .put(IntelligentCache.KEY_KEEP,
                        if (BuildConfig.DEBUG) LeakCanary.install(application)
                        else RefWatcher.DISABLED)
        //扩展 AppManager 的远程遥控功能
        ArmsUtils.obtainAppComponentFromContext(application).appManager().setHandleListener { appManager, message ->
            when (message.what) {

            }//case 0:
            //do something ...
            //   break;
        }
        //Usage:
        //Message msg = new Message();
        //msg.what = 0;
        //AppManager.post(msg); like EventBus
        ARouter.openLog()     // 打印日志
        ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(application)

        Preference.initPreferences(application)
    }

    override fun onTerminate(application: Application) {
    }
}