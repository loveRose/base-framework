package com.lvyerose.framework.base.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.lvyerose.framework.base.utils.ICoroutineDefault
import com.lvyerose.framework.base.utils.RxLifecycleManager
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseModel : IModel, LifecycleObserver, CoroutineScope by MainScope(), ICoroutineDefault {

    var rxLifecycleManager: RxLifecycleManager? = null

    override fun addDisposable(disposable: Disposable) {
        if (rxLifecycleManager == null) {
            rxLifecycleManager = RxLifecycleManager()
        }
        rxLifecycleManager?.addObserver(disposable)
    }

    override fun addDisposable(disposable: DisposableObserver<*>) {
        if (rxLifecycleManager == null) {
            rxLifecycleManager = RxLifecycleManager()
        }
        rxLifecycleManager?.addObserver(disposable)
    }

    override fun onDetach() {
        unDispose()
    }

    private fun unDispose() {
        rxLifecycleManager?.clear()
        rxLifecycleManager = null
        //取消协程域下所有协程
        cancel()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        onDetach()
        owner.lifecycle.removeObserver(this)
    }

}