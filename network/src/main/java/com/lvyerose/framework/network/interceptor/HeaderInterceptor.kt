package com.lvyerose.framework.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 *
 * 网络请求Header信息修改拦截器 可直接使用
 */
class HeaderInterceptor(private val headers: Map<String, String>?) :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
            .newBuilder()
        headers?.let {
            if (headers.isNotEmpty()) {
                val keys = headers.keys
                for (headerKey in keys) {
                    builder.addHeader(headerKey, headers[headerKey]!!).build()
                }
            }
        }
        return chain.proceed(builder.build())
    }

}