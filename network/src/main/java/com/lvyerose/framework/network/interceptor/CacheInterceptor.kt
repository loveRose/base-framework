package com.lvyerose.framework.network.interceptor

import android.content.Context
import android.util.Log
import com.lvyerose.framework.network.utils.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * *******示例********* 可直接使用
 * 网络请求缓存拦截器
 */
class CacheInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        return if (NetworkUtils.isNetworkAvailable(context)) {
            val response = chain.proceed(request)
            // read from cache for 60 s
            val maxAge = 60
            val cacheControl = request.cacheControl.toString()
            Log.e("CacheInterceptor", "60s load cahe$cacheControl")
            response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            Log.e("CacheInterceptor", " no network load cahe")
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
            val response = chain.proceed(request)
            //set cache times is 3 days
            val maxStale = 60 * 60 * 24 * 3
            response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
    }

}
