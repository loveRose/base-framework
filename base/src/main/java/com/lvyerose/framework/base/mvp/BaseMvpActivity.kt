package com.lvyerose.framework.base.mvp

import androidx.annotation.Nullable
import com.lvyerose.framework.base.general.BaseActivity

abstract class BaseMvpActivity<P : BasePresenter<BaseView, BaseModel>, M : BaseModel> : BaseActivity(), BaseView {
    @Nullable
    @JvmField
    var mPresenter: P? = null
    @Nullable
    @JvmField
    var mModel: M? = null

    override fun onAfterSetView() {
        super.onAfterSetView()
        mPresenter?.attachViewModel(this, mModel!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        mPresenter?.onDestroy()
    }
}