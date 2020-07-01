package com.lvyerose.framework.network.utils

import android.content.Context
import android.net.ConnectivityManager


/**
 * Created by lvyeRose on 2018/3/16.
 * 网络相关判断工具
 */
object NetworkUtils {
    /**
     * 网络是否可用
     *
     * @param activity
     * @return
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val mConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfo = mConnectivityManager.activeNetworkInfo
        return mNetworkInfo != null && mNetworkInfo.isConnected
    }
}
