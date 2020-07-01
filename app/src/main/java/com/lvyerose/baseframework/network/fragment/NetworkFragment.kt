package com.lvyerose.baseframework.network.fragment

import android.util.Log
import com.lvyerose.baseframework.R
import com.lvyerose.baseframework.network.config.Api
import com.lvyerose.framework.base.general.BaseFragment
import com.lvyerose.framework.network.HttpClient
import kotlinx.android.synthetic.main.activity_network_main.*
import kotlinx.coroutines.delay

class NetworkFragment : BaseFragment() {
    override fun onContentLayoutId() = R.layout.fragment_network_layout


    open fun sendNetworkTest() {
        //一个最完整的网络请求
        executeCoroutine(
            onStart = {
                "fragment开始请求".toast()
            },
            work = {
                //协程切换的IO线程域
                delay(2000)
                HttpClient.service(Api::class.java).test()
            },
            onSuccess = {
                //it为请求成功之后传入的数据对象
                tv_network_init_status.text = "fragment:" + it.toString()
            },
            onFail = { code: Int, message: String? ->
                //出现业务问题 在转换器中主动发起异常
                message?.toast()
            },
            onCompleted = {
                "fragment完成请求".toast()
            },
            onError = {
                //出现非业务异常
                it.message?.toast()
            },
            onCancel = {
                Log.e("cancel", "cancel....")
            })
    }
}