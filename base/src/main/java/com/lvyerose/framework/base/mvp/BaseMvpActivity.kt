package com.lvyerose.framework.base.mvp

import com.lvyerose.framework.base.general.BaseActivity
import com.lvyerose.framework.base.utils.InstanceUtils

abstract class BaseMvpActivity<V : IView, P : IPresenter<V>>
    : BaseActivity(), IView {
    // 持有 presenter对象
    protected var mPresenter: P? = null

    // Activity设置View布局之后执行MVP框架实例化加载
    override fun onAfterSetView() {
        super.onAfterSetView()
        if (this is IView) {
            mPresenter = InstanceUtils.getInstance(this, 1)
        }
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mPresenter = null
    }

}