package com.lvyerose.baseframework.network.config

import com.lvyerose.baseframework.network.entity.Data
import retrofit2.http.GET

interface Api {
    @GET("version/teacherget")
    suspend fun test(): Data
}