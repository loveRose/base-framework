package com.lvyerose.baseframework.base.other

import android.os.Bundle
import com.lvyerose.baseframework.R
import com.lvyerose.framework.base.constant.TransitionMode
import com.lvyerose.framework.base.general.BaseActivity

class LoginActivity : BaseActivity() {

    override fun setContentLayoutId() = R.layout.activity_login

    override fun getOverridePendingTransitionMode(transitionMode: TransitionMode): TransitionMode {
        return super.getOverridePendingTransitionMode(TransitionMode.ZOOM)
    }

    override fun onStartAction(savedInstanceState: Bundle?) {

    }
}
