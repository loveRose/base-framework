package com.lvyerose.framework.base.mvp

import com.lvyerose.framework.base.utils.RxLifecycleManager

open abstract class BasePresenter<V : BaseView, M : BaseModel> {
    var mView: V? = null
    var mModel: M? = null
    var rxManager: RxLifecycleManager? = RxLifecycleManager()

    fun attachViewModel(view: BaseView, model: BaseModel) {
        mView = view as V
        mModel = model as M
    }

    fun detachView() {
        mView?.let { mView = null }
        mModel?.let { mModel = null }
    }

    fun onDestroy() {
        rxManager!!.clear()
        rxManager = null
    }

}