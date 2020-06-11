package com.lvyerose.framework.base.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.lvyerose.framework.base.utils.InstanceUtils
import com.lvyerose.framework.base.utils.RxLifecycleManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import org.greenrobot.eventbus.EventBus

abstract class BasePresenter<V : IView, M : IModel> : IPresenter<V>, LifecycleObserver {
    var mView: V? = null
    var mModel: M? = null

    private var rxLifecycleManager: RxLifecycleManager? = RxLifecycleManager()

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = false

    /**
     * 通过反射对泛型第二个参数进行实例化
     */
    private fun createModel() {
        mModel = InstanceUtils.getInstance(this, 1)
    }

    override fun attachView(mView: V) {
        this.mView = mView
        createModel()
        if (mView is LifecycleOwner) {
            (mView as LifecycleOwner).lifecycle.addObserver(this)
            if (mModel != null && mModel is LifecycleObserver) {
                (mView as LifecycleOwner).lifecycle.addObserver(mModel as LifecycleObserver)
            }
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    /**
     * 释放View和Model
     */
    override fun detachViewModel() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        // 保证activity结束时取消所有正在执行的订阅
        unDispose()
        mModel?.onDetach()
        mView?.let { mView = null }
        mModel?.let { mModel = null }
    }

    /**
     * 提供给外部 添加生命周期绑定的订阅
     */
    open fun addDisposable(disposable: Disposable) {
        if (rxLifecycleManager == null) {
            rxLifecycleManager = RxLifecycleManager()
        }
        rxLifecycleManager?.addObserver(disposable)
    }

    /**
     * 取消所有添加进来的订阅
     */
    private fun unDispose() {
        // 保证Activity结束时取消
        rxLifecycleManager!!.clear()
        rxLifecycleManager = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        detachViewModel()
        owner.lifecycle.removeObserver(this)
    }


}