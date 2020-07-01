package com.lvyerose.framework.network.interceptor

import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

/**
 * *******示例*********
 * 添加拦截器 拦截网络请求并添加公共参数
 */
class CommonParamsInterceptor(private val interceptorParams: (HttpUrl.Builder, MutableMap<String, Any?>) -> Unit = { _, _ -> }) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val paramsMap: MutableMap<String, Any?> = HashMap()
        //获取原有请求里面的参数 (不包括后续添加的)
        // paramsMap 存储了原有的所有参数,可对其整体进行签名 或者单独对某一个参数进行修改等
        if (oldRequest.body is FormBody) {
            val oldFormBody = oldRequest.body as FormBody?
            for (i in 0 until oldFormBody!!.size) {
                paramsMap[oldFormBody.encodedName(i)] = oldFormBody.encodedValue(i)
            }
        }
        // 添加新的参数
        val authorizedUrlBuilder = oldRequest.url
            .newBuilder()
            .scheme(oldRequest.url.scheme)
            .host(oldRequest.url.host)
//            .addQueryParameter(
//                "params1", "test"
//            )
//            .addQueryParameter(
//                "params2",
//                "test"
//            )

        interceptorParams.invoke(authorizedUrlBuilder, paramsMap)

        // 组装成新的请求
        val newRequest = oldRequest.newBuilder()
            .method(oldRequest.method, oldRequest.body)
            .url(authorizedUrlBuilder.build())
            .build()
        return chain.proceed(newRequest)
    }
}
