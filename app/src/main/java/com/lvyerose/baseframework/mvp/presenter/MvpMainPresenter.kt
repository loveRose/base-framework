package com.lvyerose.baseframework.mvp.presenter

import android.content.Intent
import android.database.Observable
import com.lvyerose.baseframework.mvp.MvpMainView
import com.lvyerose.baseframework.mvp.model.MvpMainModel
import com.lvyerose.framework.base.mvp.BasePresenter
import io.reactivex.Flowable
import io.reactivex.Observer
import java.util.concurrent.TimeUnit

class MvpMainPresenter : BasePresenter<MvpMainView, MvpMainModel>() {

    fun sendNetwork(params: String) {
        mView?.startPresenter()
        mModel?.sendHttpData(params)
        addDisposable(Flowable.just("Hello world").subscribe(System.out::println))
        mView?.errorCallback("参数${params}执行错我")
    }
}