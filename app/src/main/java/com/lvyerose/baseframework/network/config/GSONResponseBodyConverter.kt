package com.lvyerose.baseframework.network.config

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.Type

class GSONResponseBodyConverter<T> constructor(var gson: Gson, var type: Type) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T {
        var response = value.string()
        //先将返回的json数据解析到Response中，如果code==200，则解析到我们的实体基类中，否则抛异常
        var result: BaseEntity<*>? = gson.fromJson(response, BaseEntity::class.java)
        result?.let {
            when (result.errno) {
                0 -> return gson.fromJson(response, this.type)
                else -> error("errno != 0")
            }
        }
        error("error")

    }

}