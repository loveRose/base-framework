package com.lvyerose.baseframework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lvyerose.baseframework.base.BaseTestActivity
import com.lvyerose.baseframework.tools.ToolsTestActivity
import com.lvyerose.framework.base.general.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 首页 进入各个模块
 */
class MainActivity : BaseActivity() {

    override fun setContentLayoutId() = R.layout.activity_main

    override fun startAction(savedInstanceState: Bundle?) {
        btn_base_test.setOnClickListener { startActivity(Intent(this, BaseTestActivity::class.java)) }
        btn_tools_test.setOnClickListener { startActivity(Intent(this, ToolsTestActivity::class.java)) }
        btn_network_test.setOnClickListener { "暂未开放".toast() }

    }

}
