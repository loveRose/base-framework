package com.lvyerose.baseframework.network.config

data class BaseEntity<T>(
    val errno: Int = 0,
    val data: T
)