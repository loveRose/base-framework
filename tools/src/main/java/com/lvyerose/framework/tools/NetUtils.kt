package com.lvyerose.framework.tools

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * desc: 网络辅助类
 * author: lad
 */
class NetUtils private constructor() {
    companion object {
        /**
         * 判断网络是否连接
         */
        fun isConnected(context: Context): Boolean {
            val connectivity =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            @SuppressLint("MissingPermission") val info =
                connectivity.activeNetworkInfo
            if (null != info && info.isConnected) {
                if (info.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
            return false
        }

        /**
         * 判断是否是wifi连接
         */
        @SuppressLint("MissingPermission")
        fun isWifi(context: Context): Boolean {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return false
            return cm.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
        }

        /**
         * 打开网络设置界面
         */
        fun openSetting(activity: Activity) {
            val intent = Intent("/")
            val cm = ComponentName(
                "com.android.settings",
                "com.android.settings.WirelessSettings"
            )
            intent.component = cm
            intent.action = "android.intent.action.VIEW"
            activity.startActivityForResult(intent, 0)
        }
    }

    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }
}