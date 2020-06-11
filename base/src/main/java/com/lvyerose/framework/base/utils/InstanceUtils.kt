package com.lvyerose.framework.base.utils

import java.lang.reflect.ParameterizedType


/**
 * 创建实例
 */
object InstanceUtils {
    fun <T> getInstance(obj: Any, i: Int): T? {
        try {
            return ((obj.javaClass
                .genericSuperclass as ParameterizedType).actualTypeArguments[i] as Class<T>)
                .newInstance()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
        return null
    }
}