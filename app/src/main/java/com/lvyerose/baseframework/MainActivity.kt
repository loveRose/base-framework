package com.lvyerose.baseframework

import android.content.Intent
import android.os.Bundle
import com.lvyerose.baseframework.base.BaseTestActivity
import com.lvyerose.baseframework.tools.ToolsTestActivity
import com.lvyerose.framework.base.general.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay

/**
 * 首页 进入各个模块
 */
class MainActivity : BaseActivity() {

    override fun setContentLayoutId() = R.layout.activity_main

    override fun onStartAction(savedInstanceState: Bundle?) {
        btn_base_test.setOnClickListener { startActivity(Intent(this, BaseTestActivity::class.java)) }
        btn_tools_test.setOnClickListener { startActivity(Intent(this, ToolsTestActivity::class.java)) }
        btn_network_test.setOnClickListener { "暂未开放".toast() }

        /**
         * 协程工具简单使用示例
         */
        executeCoroutine(
            this.coroutineContext,
            work = {
                //工作线程处理逻辑
                delay(2000)
                "延时了2秒"
            },
            onSuccess = {
                //处理完成之后的逻辑
                it.toast()
            },
            onFail = {
                //处理失败之后的逻辑
                "work返回了null".toast()
            },
            onError = {
                //处理出现异常之后的逻辑
                "出现了异常".toast()
            })
    }

}
