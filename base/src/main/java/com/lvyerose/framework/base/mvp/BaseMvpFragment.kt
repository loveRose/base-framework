package com.lvyerose.framework.base.mvp

import android.view.View
import com.lvyerose.framework.base.general.BaseFragment
import com.lvyerose.framework.base.utils.InstanceUtils

/**
 */
abstract class BaseMvpFragment<V : IView, P : IPresenter<V>>
    : BaseFragment(), IView {

    // 持有 presenter对象
    protected var mPresenter: P? = null

    override fun onStartAction(parentView: View) {
        super.onStartAction(parentView)
        mPresenter = InstanceUtils.getInstance(this, 1)
        mPresenter?.let { it.attachView(this as V) }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mPresenter = null
    }

}