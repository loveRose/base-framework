package com.lvyerose.framework.base.mvp

interface IPresenter<in V : IView> {

    /**
     * 绑定 View
     */
    fun attachView(mView: V)

    /**
     * 解绑 View和Model
     */
    fun detachViewModel()

}