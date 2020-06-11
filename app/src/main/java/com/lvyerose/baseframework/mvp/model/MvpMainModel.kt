package com.lvyerose.baseframework.mvp.model

import android.util.Log
import com.lvyerose.framework.base.mvp.BaseModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MvpMainModel : BaseModel() {

    fun sendHttpData(params: String) {
//        var ob: Observable<Long> = Observable.timer(4000, TimeUnit.MILLISECONDS)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//        ob.subscribe(object : Observer<Long> {
//
//        })
//        addDisposable(ob.subscribe())
        Log.e("mvp", "string:${params}")
    }

}