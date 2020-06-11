package com.lvyerose.framework.base.mvp

import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

interface IModel {

    fun addDisposable(disposable: DisposableObserver<*>)

    fun addDisposable(disposable: Disposable)

    fun onDetach()

}