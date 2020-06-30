package com.lvyerose.baseframework.network.config

import retrofit2.http.GET

interface Api {
    @GET("version/teacherget")
    suspend fun test(): Data
}