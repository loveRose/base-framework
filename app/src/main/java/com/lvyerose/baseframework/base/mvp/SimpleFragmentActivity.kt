package com.lvyerose.baseframework.base.mvp

import android.os.Bundle
import com.lvyerose.baseframework.R
import com.lvyerose.baseframework.base.mvp.fragment.SimpleFragment
import com.lvyerose.framework.base.general.BaseActivity

class SimpleFragmentActivity : BaseActivity() {

    override fun setContentLayoutId() = R.layout.activity_simple_fragment

    override fun onStartAction(savedInstanceState: Bundle?) {
        var fragment = SimpleFragment()
        fragment.setArgumentData("参数传递测试")
        supportFragmentManager.beginTransaction().add(R.id.content_fragment_id, fragment).commit()

    }
}
