package com.lvyerose.baseframework.base.mvp.model

import com.lvyerose.framework.base.mvp.BaseModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MvpMainModel : BaseModel() {

    fun sendHttpData(params: String, subscribe: (Observable<Long>) -> Disposable) {
        addDisposable(
            subscribe(
                Observable
                    .timer(2, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            )
        )
    }

    fun sendErrorHttpData(params: String, sub: (ob: Observable<Long>) -> Disposable) {
        addDisposable(
            sub(
                Observable
                    .timer(2, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            )
        )
    }

    fun sendHttpDataClose(params: String) {
        addDisposable(
            Observable
                .timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    // named arguments for lambda Subscribers
                    onNext = { println("close--success---2s") },
                    onError = { it.printStackTrace() },
                    onComplete = { println("close--success--Done!") }
                )
        )
    }

}