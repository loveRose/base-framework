package com.lvyerose.baseframework.network.config

import com.google.gson.Gson
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ResponseConverterFactory private constructor(var gson: Gson) : Converter.Factory() {
    init {
        if (gson == null) throw NullPointerException("gson == null")
        this.gson = gson
    }


    companion object {
        internal fun create(): ResponseConverterFactory {
            return ResponseConverterFactory(Gson())
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, Type> { //返回我们自定义的Gson响应体变换器
        return GSONResponseBodyConverter(gson, type)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation?>?,
        methodAnnotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<*, RequestBody?>? { //返回我们自定义的Gson响应体变换器
        return GSONResponseBodyConverter(gson, type)
    }
}