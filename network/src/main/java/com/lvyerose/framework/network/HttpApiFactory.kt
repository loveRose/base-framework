package com.lvyerose.framework.network

/**
 * 工场方法，提供HttpApi实例
 */
object HttpApiFactory {
    fun a() {
        HttpClient.init("", true)
    }
}