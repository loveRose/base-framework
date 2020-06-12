package com.lvyerose.baseframework.mvp.presenter

import com.lvyerose.baseframework.mvp.MvpMainView
import com.lvyerose.baseframework.mvp.model.MvpMainModel
import com.lvyerose.framework.base.mvp.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MvpMainPresenter : BasePresenter<MvpMainView, MvpMainModel>() {

    fun sendNetwork(params: String) {
        mView?.startPresenter()
        mModel?.sendHttpData(params)
        {
            it.subscribeBy(
                // named arguments for lambda Subscribers
                onNext = { mView?.showHint("参数${params}执行") },
                onError = {},
                onComplete = { println("::success--Done!") }
            )
        }
        addDisposable(
            Observable.timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    // named arguments for lambda Subscribers
                    onNext = { mView?.showHint("参数${params}执行") },
                    onError = { it.printStackTrace() },
                    onComplete = { println("::success--Done!") }
                )
        )
    }

    fun sendErrorNetwork(params: String) {
        mView?.startPresenter()
        mModel?.sendErrorHttpData(params) {
            it.subscribeBy(
                // named arguments for lambda Subscribers
                onNext = { println("fail---2s") },
                onError = { it.printStackTrace() },
                onComplete = { println("fail--Done!") }
            )
        }
        addDisposable(
            Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    // named arguments for lambda Subscribers
                    onNext = {
                        mView?.showHint("参数${params}执行")
                    },
                    onError = { it.printStackTrace() },
                    onComplete = { println("error::success--Done!") }
                )
        )
    }

    fun sendCloseNetwork(params: String) {
        mView?.startPresenter()
        mModel?.sendHttpDataClose(params)
        addDisposable(
            Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    // named arguments for lambda Subscribers
                    onNext = {
                        mView?.showHint("参数${params}执行")
                    },
                    onError = { it.printStackTrace() },
                    onComplete = { println("close::success--Done!") }
                )
        )
    }
}