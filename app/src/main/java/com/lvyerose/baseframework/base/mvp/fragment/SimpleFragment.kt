package com.lvyerose.baseframework.base.mvp.fragment

import android.os.Bundle
import android.view.View
import com.lvyerose.baseframework.R
import com.lvyerose.framework.base.general.BaseFragment
import kotlinx.android.synthetic.main.fragment_simple_layout.*

class SimpleFragment : BaseFragment() {
    var simpleValue: String? = null
    override fun onArgumentAction(bundle: Bundle?) {
        simpleValue = bundle?.get(simpleKeyValue) as String?
    }

    override fun onContentLayoutId() = R.layout.fragment_simple_layout

    override fun onStartAction(parentView: View) {
        tv_argument.text = simpleValue
    }
}