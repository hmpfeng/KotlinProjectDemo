package com.wsst.lxns.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.jess.arms.base.delegate.IFragment
import com.jess.arms.integration.cache.Cache
import com.jess.arms.integration.cache.CacheType
import com.jess.arms.integration.lifecycle.FragmentLifecycleable
import com.jess.arms.mvp.IPresenter
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.RxLifecycleUtils
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by 谢岳峰 on 2018/10/22.
 */
abstract class BaseFragment<P : IPresenter> : Fragment(), IFragment, FragmentLifecycleable {

    protected val TAG: String = this.javaClass.simpleName

    private val mLifecycleSubject: BehaviorSubject<FragmentEvent> = BehaviorSubject.create()

    private var mCache: Cache<String, Any>? = null
    @JvmField
    @Inject
    protected var mPresenter: P? = null

    override fun provideCache(): Cache<String, Any> {
        if (mCache == null)
            mCache = ArmsUtils
                    .obtainAppComponentFromContext(activity)
                    .cacheFactory()
                    .build(CacheType.FRAGMENT_CACHE) as Cache<String, Any>
        return mCache as Cache<String, Any>
    }

    override fun provideLifecycleSubject(): Subject<FragmentEvent> {
        return mLifecycleSubject
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView(inflater, container, savedInstanceState)
    }

    protected fun createViewBefore(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return null
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var createView: View? = createViewBefore(inflater, container, savedInstanceState)
        if (createView == null)
            createView = inflater.inflate(layoutRes, container, false)
        return createView!!
    }

    protected abstract val layoutRes: Int

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null)
            mPresenter!!.onDestroy()//释放资源
        mPresenter = null
    }

    fun addRxClick(view: View) {
        RxView.clicks(view)
                .throttleFirst(1, TimeUnit.SECONDS)
                .map { return@map view }
                .compose(RxLifecycleUtils.bindToLifecycle(this))
                .subscribe { onRxClick(view) }
    }

    fun onRxClick(view: View) {

    }

    override fun useEventBus(): Boolean {
        return true
    }
}