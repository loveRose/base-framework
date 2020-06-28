package com.lvyerose.framework.network

/**
 * 网络请求基本配置
 */
class HttpApi private constructor(builder: Builder) {
    internal var baseUrl: String? = null

    init {
        baseUrl = builder.baseUrl
    }



    class Builder {
        internal var baseUrl: String? = null

        fun baseUrl(baseUrl: String): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun build(): HttpApi {
            return HttpApi(this)
        }
    }

}