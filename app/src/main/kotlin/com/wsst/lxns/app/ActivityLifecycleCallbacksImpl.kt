package com.wsst.lxns.app

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.jess.arms.widget.autolayout.AutoToolbar
import com.orhanobut.logger.Logger
import com.wsst.lxns.R
import com.zhy.autolayout.AutoRelativeLayout


/**
 * Created by 谢岳峰 on 2018/10/19.
 */
class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {
        Logger.w(activity.toString() + " - onActivityPaused")
    }

    override fun onActivityResumed(activity: Activity?) {
        Logger.w(activity.toString() + " - onActivityResumed")
    }

    override fun onActivityStarted(activity: Activity?) {
        if (!activity!!.intent.getBooleanExtra("isInitToolbar", false)) {
            //由于加强框架的兼容性,故将 setContentView 放到 onActivityCreated 之后,onActivityStarted 之前执行
            //而 findViewById 必须在 Activity setContentView() 后才有效,所以将以下代码从之前的 onActivityCreated 中移动到 onActivityStarted 中执行
            activity.intent.putExtra("isInitToolbar", true)
            //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
            if (activity.findViewById<AutoToolbar>(R.id.toolbar) != null) {
                if (activity is AppCompatActivity) {
                    activity.setSupportActionBar(activity.findViewById(R.id.toolbar) as Toolbar)
                    activity.supportActionBar!!.setDisplayShowTitleEnabled(false)
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activity.setActionBar(activity.findViewById(R.id.toolbar) as android.widget.Toolbar)
                        activity.getActionBar().setDisplayShowTitleEnabled(false)
                    }
                }
            }
            if (activity.findViewById<TextView>(R.id.toolbar_title) != null) {
                (activity.findViewById(R.id.toolbar_title) as TextView).text = activity.getTitle()
            }
            if (activity.findViewById<AutoRelativeLayout>(R.id.toolbar_back) != null) {
                activity.findViewById<AutoRelativeLayout>(R.id.toolbar_back).setOnClickListener(
                        { v -> activity.onBackPressed() })
            }
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        Logger.w(activity.toString() + " - onActivityDestroyed")
        //横竖屏切换或配置改变时, Activity 会被重新创建实例, 但 Bundle 中的基础数据会被保存下来,移除该数据是为了保证重新创建的实例可以正常工作
        activity?.getIntent()?.removeExtra("isInitToolbar")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        Logger.w(activity.toString() + " - onActivitySaveInstanceState")
    }

    override fun onActivityStopped(activity: Activity?) {
        Logger.w(activity.toString() + " - onActivityStopped")
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Logger.w(activity.toString() + " - onActivityCreated")
    }
}