package com.lvyerose.baseframework.mvp

import com.lvyerose.baseframework.mvp.base.BaseBusinessView

interface MvpMainView : BaseBusinessView {
    fun errorCallback(msg: String)
}