package com.lvyerose.baseframework.network.config

import com.google.gson.Gson
import com.lvyerose.framework.base.exception.BusinessException
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.Type

class GSONResponseBodyConverter<T> constructor(var gson: Gson, var type: Type) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T {
        var response = value.string()
        var result = gson.fromJson(response, BaseEntity::class.java)
        result?.let {
            when (result.errno) {
                //业务正常传递
                0 -> {
                    var dataJson = gson.toJson(result.data)
                    return gson.fromJson(dataJson, type)
                }
                //业务逻辑 主动发起异常 会被 onFail 捕获
                else -> throw BusinessException(result.errno, result.message)
            }
        }
        //正常触发异常 会被 onError捕获
        error("请求结果为空")
    }

}