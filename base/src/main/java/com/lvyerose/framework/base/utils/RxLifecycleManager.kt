package com.lvyerose.framework.base.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver


/**
 * RxJava提供的DisposableObserver生命周期管理
 * 应用场景：
 * 1、RxJava网络请求的时候订阅发起请求会返回一个DisposableObserver，将其add到管理类中，即可在界面销毁时清理出队列
 * 2、异步耗时任务需要与界面生命周期同步的形式
 */
class RxLifecycleManager {
    private val compositeDisposable = CompositeDisposable()

    fun addObserver(observer: DisposableObserver<*>) {
        observer?.let { compositeDisposable?.add(it) }
    }

    fun addObserver(disposable: Disposable) {
        disposable?.let { compositeDisposable?.add(it) }
    }

    fun clear() {
        compositeDisposable.clear()
    }
}