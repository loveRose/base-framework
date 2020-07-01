package com.lvyerose.baseframework.network.entity

data class BaseEntity<T>(
    val errno: Int = 0,
    val data: T,
    val message: String = ""
)