package com.lvyerose.baseframework.mvp

import com.lvyerose.baseframework.mvp.base.BaseBusinessView

interface MvpMainView : BaseBusinessView {
    fun showHint(msg: String)
}