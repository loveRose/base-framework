package com.lvyerose.baseframework.network

import android.os.Bundle
import com.lvyerose.baseframework.R
import com.lvyerose.baseframework.network.config.Api
import com.lvyerose.baseframework.network.config.ResponseConverterFactory
import com.lvyerose.framework.base.general.BaseActivity
import com.lvyerose.framework.network.HttpClient
import kotlinx.android.synthetic.main.activity_network_main.*

class NetworkMainActivity : BaseActivity() {
    var baseUrl = "https://api.meiyuanbang.com/"
    override fun setContentLayoutId() = R.layout.activity_network_main

    override fun onStartAction(savedInstanceState: Bundle?) {
        tv_network_init_status.text = "网络模块初始化状态：正在初始化"
        HttpClient.apply {
            init(baseUrl, true)
            configRetrofitBuild {
                it.addConverterFactory(ResponseConverterFactory.create())
            }
            initDoneWith()
        }

        tv_network_init_status.text = "网络模块初始化状态：加载完成"
        btn_activity_network.setOnClickListener {
            executeCoroutine(work = {
                HttpClient.service(Api::class.java).test()
            }, onSuccess = {
                tv_network_init_status.text = it.toString()
            })
        }
    }
}
