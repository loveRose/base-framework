package com.lvyerose.framework.base.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.lvyerose.framework.base.utils.ICoroutineDefault
import com.lvyerose.framework.base.utils.InstanceUtils
import com.lvyerose.framework.base.utils.RxLifecycleManager
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import org.greenrobot.eventbus.EventBus

abstract class BasePresenter<V : IView, M : IModel> : IPresenter<V>, LifecycleObserver, CoroutineScope by MainScope(),
    ICoroutineDefault {
    var mView: V? = null
    var mModel: M? = null
    open var otherModel: IModel? = null
    var otherModelList = mutableListOf<IModel>()

    private var rxLifecycleManager: RxLifecycleManager? = RxLifecycleManager()

    /**
     * 是否使用 EventBus
     */
    fun useEventBus(): Boolean = false

    /**
     * 通过反射对泛型第二个参数进行实例化
     */
    private fun createModel() {
        mModel = InstanceUtils.getInstance(this, 1)
    }

    /**
     * 提供外部监听
     * 当mvp框架组装完成的时候
     * 用于嵌入额外逻辑，如：添加多个Model的情况
     */
    open fun onAttachStartAction() {

    }

    /**
     * 提供额外Model创建功能
     */
    open fun createOtherModel(): IModel? {
        return null
    }

    /**
     * 提供额外Model集合添加
     */
    fun addModelList(model: IModel) {
        otherModelList.add(model)
    }

    fun getModeByIndex(index: Int): IModel? {
        if (index >= 0 && index < otherModelList.size) {
            return otherModelList[index]
        }
        return null
    }

    override fun attachView(mView: V) {
        this.mView = mView
        createModel()
        otherModel = createOtherModel()
        if (mView is LifecycleOwner) {
            (mView as LifecycleOwner).lifecycle.addObserver(this)
            if (mModel != null && mModel is LifecycleObserver) {
                (mView as LifecycleOwner).lifecycle.addObserver(mModel as LifecycleObserver)
            }
            if (otherModel != null && otherModel is LifecycleObserver) {
                (mView as LifecycleOwner).lifecycle.addObserver(otherModel as LifecycleObserver)
            }
            onAttachStartAction()
            for (modelItem in otherModelList) {
                if (modelItem != null && modelItem is LifecycleObserver) {
                    (mView as LifecycleOwner).lifecycle.addObserver(modelItem as LifecycleObserver)
                }
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
        mView?.let { mView = null }

        //释放Model
        mModel?.let {
            it.onDetach()
            mModel = null
        }
        otherModel?.let {
            it.onDetach()
            otherModel = null
        }
        for (modelItem in otherModelList) {
            modelItem?.let {
                it.onDetach()
            }
        }
        otherModelList.clear()
    }

    /**
     * 提供给外部 添加生命周期绑定的订阅
     */
    fun addDisposable(disposable: Disposable) {
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
        //取消协程域下的所有协程
        cancel()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        detachViewModel()
        owner.lifecycle.removeObserver(this)
    }


}