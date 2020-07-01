package com.lvyerose.baseframework.network

import android.os.Bundle
import com.lvyerose.baseframework.R
import com.lvyerose.baseframework.network.config.Api
import com.lvyerose.baseframework.network.config.converter.ResponseConverterFactory
import com.lvyerose.baseframework.network.fragment.NetworkFragment
import com.lvyerose.framework.base.general.BaseActivity
import com.lvyerose.framework.network.HttpClient
import com.lvyerose.framework.network.interceptor.CacheInterceptor
import com.lvyerose.framework.network.interceptor.CommonParamsInterceptor
import com.lvyerose.framework.network.interceptor.HeaderInterceptor
import kotlinx.android.synthetic.main.activity_mvp_main.*
import kotlinx.android.synthetic.main.activity_network_main.*
import kotlinx.coroutines.delay

class NetworkMainActivity : BaseActivity() {
    private var baseUrl = "https://api.meiyuanbang.com/"
    override fun setContentLayoutId() = R.layout.activity_network_main

    override fun onStartAction(savedInstanceState: Bundle?) {
        tv_network_init_status.text = "网络模块初始化状态：正在初始化"
        //一般是在BaseApplication中进行初始化
        HttpClient.apply {
            init(baseUrl, true)
            configRetrofitBuild {
                it.addConverterFactory(ResponseConverterFactory.create())
            }
            configOkHttpClientBuild {
                it.addInterceptor(
                    CommonParamsInterceptor { build, _ ->
                        build.addQueryParameter("test", "test")
                    }
                )
                it.addInterceptor(
                    HeaderInterceptor(null)
                )
                it.addInterceptor(CacheInterceptor(this@NetworkMainActivity))
                //添加其他设置内容
            }
            initDoneWith()
        }
        tv_network_init_status.text = "网络模块初始化状态：加载完成"

        btn_activity_network.setOnClickListener {
            //最简单的一个网络请求
            executeCoroutine(
                work = {
                    HttpClient.service(Api::class.java).test()
                }
            )
            //一个最完整的网络请求
            executeCoroutine(
                onStart = {
                    "开始请求".toast()
                },
                work = {
                    delay(2000)
                    //协程切换的IO线程域
                    HttpClient.service(Api::class.java).test()
                },
                onSuccess = {
                    //it为请求成功之后传入的数据对象
                    tv_network_init_status.text = it.toString()
                },
                onFail = { code: Int, message: String? ->
                    //出现业务问题 在转换器中主动发起异常
                    message?.toast()
                },
                onCompleted = {
                    "完成请求".toast()
                },
                onError = {
                    //出现非业务异常
                    it.message?.toast()
                })
        }

        btn_fragment_network.setOnClickListener {
            var fragment = fragment_view as NetworkFragment
            fragment.sendNetworkTest()
        }
    }
}
