package com.lvyerose.baseframework.base.mvp

import com.lvyerose.baseframework.base.mvp.base.BaseBusinessView

interface MvpMainView : BaseBusinessView {
    fun showHint(msg: String)
}