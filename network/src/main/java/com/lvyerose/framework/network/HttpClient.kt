package com.lvyerose.framework.network

import android.util.Log
import com.lvyerose.framework.network.converter.StringConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * 网络请求基本配置
 */
class HttpClient private constructor() {
    private var retrofit: Retrofit? = null
    private var retrofitBuild: Retrofit.Builder? = null
    private var okHttpClientBuild: OkHttpClient.Builder? = null

    companion object {
        private val instance: HttpClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpClient()
        }

        /**
         * 初始化网络请求模块
         */
        fun init(baseUrl: String, debugEnable: Boolean): HttpClient {
            retrofitBuildInit(baseUrl)
            okHttpClientBuildInit(debugEnable)
            return instance
        }

        /**
         * 初始化 init() 方法之后可对Retrofit-Build进行配置
         * 调用此方法添加转换器等
         */
        fun configRetrofitBuild(otherConverterFactory: (build: Retrofit.Builder) -> Retrofit.Builder?): HttpClient {
            instance.retrofitBuild ?: error("必须先调用init()方法进行初始化")
            otherConverterFactory?.let {
                otherConverterFactory.invoke(instance.retrofitBuild!!)
            }
            return instance
        }

        /**
         * 初始化 init()方法之后可对OkHttpClient-Build进行配置
         * 调用此方法添加拦截器、超时设置、重定向设置等
         */
        fun configOkHttpClientBuild(otherInterceptor: (build: OkHttpClient.Builder) -> OkHttpClient.Builder?): HttpClient {
            instance.okHttpClientBuild ?: error("必须先调用init()方法进行初始化")
            otherInterceptor?.let {
                otherInterceptor.invoke(instance.okHttpClientBuild!!)
            }
            return instance
        }

        /**
         * 网络模块装配完成
         */
        fun initDoneWith(): HttpClient {
            instance.retrofitBuild ?: error("必须先调用init()方法进行初始化")
            instance.okHttpClientBuild ?: error("必须先调用init()方法进行初始化")
            instance.retrofit = instance.retrofitBuild?.apply {
                client(instance.okHttpClientBuild?.build())
            }!!.build()
            return instance
        }

        /**
         * 获取API服务
         */
        fun <T> service(service: Class<T>): T {
            if (instance.retrofit == null) {
                if (instance.retrofitBuild != null) {
                    //已经初始化init()方法了
                    error("必须先调用HttpClient.initDoneWith()方法完成初始化配置")
                } else {
                    //未初始化init()方法
                    error("必须先调用HttpClient.init()方法进行初始化")
                }
            } else {
                return instance.retrofit!!.create(service)
            }
        }


        //初始化 Retrofit Build
        private fun retrofitBuildInit(baseUrl: String) {
            instance.retrofitBuild = Retrofit.Builder().apply {
                baseUrl(baseUrl)
            }
        }

        //初始化 OkHttpClient Build
        private fun okHttpClientBuildInit(debugEnable: Boolean = false) {
            instance.okHttpClientBuild = OkHttpClient.Builder().apply {
                connectTimeout(15, TimeUnit.SECONDS)
                readTimeout(15, TimeUnit.SECONDS)
                if (debugEnable) addInterceptor(interceptorLog())
            }
        }

        //日志打印拦截器
        private fun interceptorLog(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d("HttpClient", message)
                }
            })
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }
    }

}